package com.library;

/**
 * Represents one issue (borrow) transaction of a book to a borrower.
 * returnDate is empty string ("") while the book is still out.
 */
public class IssueRecord {
    private final String recordId;
    private final String bookId;
    private final String borrowerName;
    private final String issueDate;
    private String returnDate;

    public IssueRecord(String recordId, String bookId, String borrowerName, String issueDate, String returnDate) {
        this.recordId = recordId;
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getRecordId() { return recordId; }
    public String getBookId() { return bookId; }
    public String getBorrowerName() { return borrowerName; }
    public String getIssueDate() { return issueDate; }
    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
    public boolean isReturned() { return returnDate != null && !returnDate.isEmpty(); }

    public String toCsvLine() {
        return String.join(",", recordId, bookId, clean(borrowerName), issueDate,
                returnDate == null ? "" : returnDate);
    }

    public static IssueRecord fromCsvLine(String line) {
        String[] p = line.split(",", -1);
        return new IssueRecord(p[0], p[1], p[2], p[3], p.length > 4 ? p[4] : "");
    }

    private static String clean(String s) {
        return s == null ? "" : s.replace(",", ";");
    }

    @Override
    public String toString() {
        String status = isReturned() ? "Returned on " + returnDate : "OUTSTANDING";
        return String.format("Record#%s | BookID: %s | Borrower: %s | Issued: %s | %s",
                recordId, bookId, borrowerName, issueDate, status);
    }
}
