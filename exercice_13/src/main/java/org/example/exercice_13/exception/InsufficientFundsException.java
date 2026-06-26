package org.example.exercice_13.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(double balance, double amount) {
        super("Insufficient funds: balance=" + balance + ", requested=" + amount);
    }
}
