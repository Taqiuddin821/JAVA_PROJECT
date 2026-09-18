# READ ME FILE 
# Library Management System

A simple command-line based Library Management System developed in Java. It allows users to manage books, search the catalog, issue and return books, and maintain borrowing records.

## Features

- Add new books to the library catalog
- Remove books from the catalog
- View all available books
- Search books by:
  - Title
  - Author
  - ISBN
- Issue books to borrowers
- Return issued books
- Track available copies
- View outstanding/unreturned issues
- View complete issue history
- Store data locally using CSV files
- No database server or external service required

## Problem Statement

Small libraries often manage their books and lending records manually, which can make it difficult to track available books, issued books, and borrowers.

This project provides a lightweight command-line solution that simplifies book catalog management and the issue/return process while keeping data stored locally between sessions.

## Technologies Used

- Java
- Maven
- CSV File Storage
- Command Line Interface (CLI)

## Project Objectives

The main objectives of this project are:

1. Manage the library book catalog.
2. Search books using title, author, or ISBN.
3. Issue books to borrowers.
4. Return previously issued books.
5. Track available and issued copies.
6. Maintain borrowing history.
7. Store data permanently using local CSV files.
8. Provide a simple menu-driven command-line interface.

## How to Run

### Prerequisites

- Java JDK installed
- Maven installed

### Build the Project

```bash
mvn clean package
Menu Options

The application provides the following options:

Add a new book
View all books
Search books
Issue a book
Return a book
View outstanding issues
View full issue history
Remove a book
Exit
Future Enhancements

Possible improvements include:

Add due dates and overdue fine calculation
Add member ID and contact information
Create a dedicated Borrower entity
Replace CSV storage with SQLite
Add JUnit unit testing
Add role-based access for librarians and students
Add a fine-payment system
Conclusion

The Library Management System provides a simple and efficient way to manage books and lending activities through a command-line interface. Its local CSV storage makes the project dependency-free and easy to run, while its modular design allows future improvements and additional features.

NAME: Taqiuddin
Course: Programming in Java
Course Code: CSE2006
