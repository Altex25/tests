package org.example.exercice_13.repository;

import org.example.exercice_13.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    @Override
    public Account save(Account account) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<Account> findAll() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean existsByNumber(String number) {
        throw new UnsupportedOperationException("TODO");
    }
}
