package org.example.exercice_14.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String loanId) {
        super("Loan not found: " + loanId);
    }
}
