package com.library;

import java.util.List;
import java.util.Scanner;

/**
 * Command-line entry point for the Library Management System.
 * Presents a numbered menu and dispatches to Library operations.
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static Library library;

    public static void main(String[] args) {
        library = new Library(new FileStorage());
        System.out.println("=========================================");
        System.out.println(" Welcome to the Library Management System");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> addBook();
                case "2" -> listBooks();
                case "3" -> searchBooks();
                case "4" -> issueBook();
                case "5" -> returnBook();
                case "6" -> viewOutstanding();
                case "7" -> viewAllRecords();
                case "8" -> removeBook();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("---------------- MENU ----------------");
        System.out.println("1. Add a new book");
        System.out.println("2. View all books");
        System.out.println("3. Search books (title / author / ISBN)");
        System.out.println("4. Issue a book");
        System.out.println("5. Return a book");
        System.out.println("6. View outstanding (not yet returned) issues");
        System.out.println("7. View full issue history");
        System.out.println("8. Remove a book");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void addBook() {
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Author: ");
        String author = sc.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine().trim();
        int copies = readInt("Number of copies: ");
        if (copies < 1) {
            System.out.println("Copies must be at least 1. Book not added.");
            return;
        }
        Book book = library.addBook(title, author, isbn, copies);
        System.out.println("Added: " + book);
    }

    private static void listBooks() {
        List<Book> all = library.listBooks().stream().toList();
        if (all.isEmpty()) {
            System.out.println("No books in the catalog yet.");
            return;
        }
        System.out.println("Catalog (" + all.size() + " titles):");
        for (Book b : all) System.out.println("  " + b);
    }

    private static void searchBooks() {
        System.out.print("Enter search keyword: ");
        String keyword = sc.nextLine().trim();
        List<Book> results = library.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }
        System.out.println("Found " + results.size() + " result(s):");
        for (Book b : results) System.out.println("  " + b);
    }

    private static void issueBook() {
        System.out.print("Book ID to issue: ");
        String bookId = sc.nextLine().trim();
        System.out.print("Borrower name: ");
        String borrower = sc.nextLine().trim();
        String result = library.issueBook(bookId, borrower);
        if (result == null) {
            System.out.println("No book found with ID " + bookId);
        } else if (result.isEmpty()) {
            System.out.println("No copies available for that book right now.");
        } else {
            System.out.println("Issued! Record ID: " + result + " (keep this to process the return later)");
        }
    }

    private static void returnBook() {
        System.out.print("Record ID to return: ");
        String recordId = sc.nextLine().trim();
        boolean ok = library.returnBook(recordId);
        System.out.println(ok ? "Book marked as returned." : "No outstanding record found with that ID.");
    }

    private static void viewOutstanding() {
        List<IssueRecord> outstanding = library.listOutstanding();
        if (outstanding.isEmpty()) {
            System.out.println("No outstanding issues. Everything is checked in!");
            return;
        }
        System.out.println("Outstanding issues:");
        for (IssueRecord r : outstanding) System.out.println("  " + r);
    }

    private static void viewAllRecords() {
        List<IssueRecord> all = library.listIssueRecords();
        if (all.isEmpty()) {
            System.out.println("No issue history yet.");
            return;
        }
        System.out.println("Full issue history:");
        for (IssueRecord r : all) System.out.println("  " + r);
    }

    private static void removeBook() {
        System.out.print("Book ID to remove: ");
        String bookId = sc.nextLine().trim();
        boolean ok = library.removeBook(bookId);
        if (ok) {
            System.out.println("Book removed.");
        } else {
            System.out.println("Could not remove: either the ID doesn't exist or copies are still checked out.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
