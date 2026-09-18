package com.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Core business logic: in-memory catalog + issue ledger, backed by FileStorage.
 * All mutating operations persist to disk immediately so no data is lost
 * even if the program exits unexpectedly.
 */
public class Library {
    private final Map<String, Book> books = new LinkedHashMap<>();
    private final List<IssueRecord> issues = new ArrayList<>();
    private final FileStorage storage;
    private int nextBookId;
    private int nextRecordId;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    public Library(FileStorage storage) {
        this.storage = storage;
        for (Book b : storage.loadBooks()) books.put(b.getId(), b);
        issues.addAll(storage.loadIssues());
        nextBookId = books.keySet().stream()
                .map(id -> id.replace("B", ""))
                .mapToInt(s -> s.matches("\\d+") ? Integer.parseInt(s) : 0)
                .max().orElse(0) + 1;
        nextRecordId = issues.stream()
                .map(IssueRecord::getRecordId)
                .mapToInt(s -> s.matches("\\d+") ? Integer.parseInt(s) : 0)
                .max().orElse(0) + 1;
    }

    public Book addBook(String title, String author, String isbn, int copies) {
        String id = "B" + (nextBookId++);
        Book book = new Book(id, title, author, isbn, copies, copies);
        books.put(id, book);
        storage.saveBooks(books.values());
        return book;
    }

    public boolean removeBook(String bookId) {
        boolean hasOutstanding = issues.stream()
                .anyMatch(r -> r.getBookId().equals(bookId) && !r.isReturned());
        if (hasOutstanding) return false;
        Book removed = books.remove(bookId);
        if (removed != null) storage.saveBooks(books.values());
        return removed != null;
    }

    public Collection<Book> listBooks() {
        return books.values();
    }

    public List<Book> searchBooks(String keyword) {
        String k = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(k)
                    || b.getAuthor().toLowerCase().contains(k)
                    || b.getIsbn().toLowerCase().contains(k)) {
                result.add(b);
            }
        }
        return result;
    }

    public Optional<Book> findBook(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public String issueBook(String bookId, String borrowerName) {
        Book book = books.get(bookId);
        if (book == null) return null;
        if (book.getAvailableCopies() <= 0) return "";
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        String recordId = String.valueOf(nextRecordId++);
        IssueRecord record = new IssueRecord(recordId, bookId, borrowerName,
                LocalDate.now().format(DATE_FMT), "");
        issues.add(record);
        storage.saveBooks(books.values());
        storage.saveIssues(issues);
        return recordId;
    }

    public boolean returnBook(String recordId) {
        for (IssueRecord r : issues) {
            if (r.getRecordId().equals(recordId) && !r.isReturned()) {
                r.setReturnDate(LocalDate.now().format(DATE_FMT));
                Book book = books.get(r.getBookId());
                if (book != null) {
                    book.setAvailableCopies(Math.min(book.getTotalCopies(), book.getAvailableCopies() + 1));
                }
                storage.saveBooks(books.values());
                storage.saveIssues(issues);
                return true;
            }
        }
        return false;
    }

    public List<IssueRecord> listIssueRecords() {
        return issues;
    }

    public List<IssueRecord> listOutstanding() {
        return issues.stream().filter(r -> !r.isReturned()).toList();
    }
}
