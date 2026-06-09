package com.example.exception;

public class ProduitInconnuException extends RuntimeException {

    public ProduitInconnuException(String message) {
        super(message);
    }
}
