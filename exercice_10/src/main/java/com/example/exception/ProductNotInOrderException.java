package com.example.exception;

public class ProductNotInOrderException extends RuntimeException {

    public ProductNotInOrderException(String message) {
        super(message);
    }
}
