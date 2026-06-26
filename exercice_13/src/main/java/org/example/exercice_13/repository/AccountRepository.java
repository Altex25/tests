package org.example.exercice_13.repository;

import org.example.exercice_13.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findByNumber(String number);

    List<Account> findAll();

    boolean existsByNumber(String number);
}
