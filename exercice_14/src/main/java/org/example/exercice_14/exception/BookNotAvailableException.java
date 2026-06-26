package org.example.exercice_14.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String bookId) {
        super("Book is not available: " + bookId);
    }
}
