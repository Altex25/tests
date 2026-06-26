package org.example.exercice_13.service;

import org.example.exercice_13.model.Account;
import org.example.exercice_13.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String number, String owner) {
        throw new UnsupportedOperationException("TODO");
    }

    public Account getAccount(String number) {
        throw new UnsupportedOperationException("TODO");
    }

    public List<Account> getAllAccounts() {
        throw new UnsupportedOperationException("TODO");
    }

    public Account deposit(String number, double amount) {
        throw new UnsupportedOperationException("TODO");
    }

    public Account withdraw(String number, double amount) {
        throw new UnsupportedOperationException("TODO");
    }

    public void transfer(String fromNumber, String toNumber, double amount) {
        throw new UnsupportedOperationException("TODO");
    }
}
