package org.example.exercice_13.service;

import org.example.exercice_13.exception.AccountAlreadyExistsException;
import org.example.exercice_13.exception.AccountNotFoundException;
import org.example.exercice_13.exception.InsufficientFundsException;
import org.example.exercice_13.exception.InvalidAmountException;
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

    /**
     * Creates a new account.
     *
     * @param number The account number.
     * @param owner The account owner.
     * @return Created account.
     */
    public Account createAccount(String number, String owner) {
        if (accountRepository.existsByNumber(number)) {
            throw new AccountAlreadyExistsException(number);
        }
        return accountRepository.save(new Account(number, owner));
    }

    /**
     * Get an account by its number.
     *
     * @param number The account number.
     * @return The account.
     */
    public Account getAccount(String number) {
        return accountRepository.findByNumber(number)
                .orElseThrow(() -> new AccountNotFoundException(number));
    }

    /**
     * Get all accounts.
     *
     * @return The list of accounts.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Deposit an amount to an account.
     *
     * @param number The account number.
     * @param amount The amount to deposit.
     * @return The updated account.
     */
    public Account deposit(String number, double amount) {
        Account account = getAccount(number);
        requirePositiveAmount(amount);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    /**
     * Withdraw an amount from an account.
     *
     * @param number The account number.
     * @param amount The amount to withdraw.
     * @return The updated account.
     */
    public Account withdraw(String number, double amount) {
        Account account = getAccount(number);
        requirePositiveAmount(amount);
        requireSufficientFunds(account, amount);
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    /**
     * Transfer an amount from one account to another.
     *
     * @param fromNumber The account number to transfer from.
     * @param toNumber The account number to transfer to.
     * @param amount The amount to transfer.
     */
    public void transfer(String fromNumber, String toNumber, double amount) {
        var fromOptional = accountRepository.findByNumber(fromNumber);
        var toOptional = accountRepository.findByNumber(toNumber);
        Account from = fromOptional.orElseThrow(() -> new AccountNotFoundException(fromNumber));
        Account to = toOptional.orElseThrow(() -> new AccountNotFoundException(toNumber));
        requirePositiveAmount(amount);
        requireSufficientFunds(from, amount);
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
        accountRepository.save(from);
        accountRepository.save(to);
    }

    private void requirePositiveAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
    }

    private void requireSufficientFunds(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException(account.getBalance(), amount);
        }
    }
}
