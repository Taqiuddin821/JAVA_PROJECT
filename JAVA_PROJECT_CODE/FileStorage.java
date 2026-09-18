package com.library;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Handles all reading and writing of books.csv and issues.csv
 * inside a "data" folder next to where the program is run.
 */
public class FileStorage {
    private static final String DATA_DIR = "data";
    private static final Path BOOKS_FILE = Paths.get(DATA_DIR, "books.csv");
    private static final Path ISSUES_FILE = Paths.get(DATA_DIR, "issues.csv");

    public FileStorage() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            if (!Files.exists(BOOKS_FILE)) Files.createFile(BOOKS_FILE);
            if (!Files.exists(ISSUES_FILE)) Files.createFile(ISSUES_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize data directory: " + e.getMessage(), e);
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(BOOKS_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) books.add(Book.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read books.csv (" + e.getMessage() + ")");
        }
        return books;
    }

    public void saveBooks(Collection<Book> books) {
        try (BufferedWriter bw = Files.newBufferedWriter(BOOKS_FILE, StandardCharsets.UTF_8)) {
            for (Book b : books) {
                bw.write(b.toCsvLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save books.csv (" + e.getMessage() + ")");
        }
    }

    public List<IssueRecord> loadIssues() {
        List<IssueRecord> records = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(ISSUES_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) records.add(IssueRecord.fromCsvLine(line));
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read issues.csv (" + e.getMessage() + ")");
        }
        return records;
    }

    public void saveIssues(Collection<IssueRecord> records) {
        try (BufferedWriter bw = Files.newBufferedWriter(ISSUES_FILE, StandardCharsets.UTF_8)) {
            for (IssueRecord r : records) {
                bw.write(r.toCsvLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save issues.csv (" + e.getMessage() + ")");
        }
    }
}
