package com.library;

/**
 * Represents a single book title held by the library.
 * totalCopies is the number of physical copies owned;
 * availableCopies is how many are currently on the shelf (not issued).
 */
public class Book {
    private final String id;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

    public Book(String id, String title, String author, String isbn, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    /** Serializes this book as one CSV line. Commas in fields are replaced with semicolons to keep parsing simple. */
    public String toCsvLine() {
        return String.join(",",
                id, clean(title), clean(author), clean(isbn),
                String.valueOf(totalCopies), String.valueOf(availableCopies));
    }

    public static Book fromCsvLine(String line) {
        String[] p = line.split(",", -1);
        return new Book(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]));
    }

    private static String clean(String s) {
        return s == null ? "" : s.replace(",", ";");
    }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" by %s | ISBN: %s | Available: %d/%d",
                id, title, author, isbn, availableCopies, totalCopies);
    }
}
