package org.example.exercice_13.exception;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(double amount) {
        super("Invalid amount: " + amount);
    }
}
