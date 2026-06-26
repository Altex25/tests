package org.example.exercice_14.repository;

import org.example.exercice_14.model.Loan;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryLoanRepository implements LoanRepository {

    private final Map<String, Loan> loans = new HashMap<>();

    @Override
    public Loan save(Loan loan) {
        loans.put(loan.getId(), loan);
        return loan;
    }

    @Override
    public Optional<Loan> findById(String id) {
        return Optional.ofNullable(loans.get(id));
    }

    @Override
    public boolean existsActiveLoanForBook(String bookId) {
        return loans.values().stream()
                .anyMatch(loan -> loan.getBookId().equals(bookId) && loan.getReturnDate() == null);
    }
}
