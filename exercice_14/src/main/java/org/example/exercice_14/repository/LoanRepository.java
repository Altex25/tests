package org.example.exercice_14.repository;

import org.example.exercice_14.model.Loan;

import java.util.Optional;

public interface LoanRepository {

    Loan save(Loan loan);

    Optional<Loan> findById(String id);

    boolean existsActiveLoanForBook(String bookId);
}
