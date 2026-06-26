package org.example.exercice_14.exception;

public class BookAvailableException extends RuntimeException {

    public BookAvailableException(String bookId) {
        super("Book is available and cannot be reserved: " + bookId);
    }
}
