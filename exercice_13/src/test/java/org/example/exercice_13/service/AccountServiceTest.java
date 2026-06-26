package org.example.exercice_13.service;

import org.example.exercice_13.exception.AccountAlreadyExistsException;
import org.example.exercice_13.exception.AccountNotFoundException;
import org.example.exercice_13.exception.InsufficientFundsException;
import org.example.exercice_13.exception.InvalidAmountException;
import org.example.exercice_13.model.Account;
import org.example.exercice_13.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void assertCreateAccountSuccess() {
        String number = "ACC001";
        String owner = "Alice";
        Account expected = new Account(number, owner);
        when(accountRepository.existsByNumber(number)).thenReturn(false);
        when(accountRepository.save(any())).thenReturn(expected);

        Account result = accountService.createAccount(number, owner);

        assertThat(result.getNumber()).isEqualTo(number);
        assertThat(result.getOwner()).isEqualTo(owner);
        assertThat(result.getBalance()).isEqualTo(0.0);
    }

    @Test
    void assertCreateAccountThrowsIfNumberAlreadyExists() {
        String number = "ACC001";
        when(accountRepository.existsByNumber(number)).thenReturn(true);

        assertThatThrownBy(() -> accountService.createAccount(number, "Alice"))
                .isInstanceOf(AccountAlreadyExistsException.class);
    }

    @Test
    void assertGetAccountByNumberReturnsExistingAccount() {
        String number = "ACC001";
        Account account = new Account(number, "Alice");
        when(accountRepository.findByNumber(number)).thenReturn(Optional.of(account));

        Account result = accountService.getAccount(number);

        assertThat(result.getNumber()).isEqualTo(number);
        assertThat(result.getOwner()).isEqualTo("Alice");
    }

    @Test
    void assertGetAccountByNumberThrowsIfNotFound() {
        String number = "UNKNOWN";
        when(accountRepository.findByNumber(number)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getAccount(number))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void assertGetAllAccountsReturnsCompleteList() {
        List<Account> accounts = List.of(new Account("ACC001", "Alice"), new Account("ACC002", "Bob"));
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertThat(result).hasSize(2);
    }

    @Test
    void assertDepositAddsAmountToBalance() {
        Account account = new Account("ACC001", "Alice");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.deposit("ACC001", 100.0);

        assertThat(result.getBalance()).isEqualTo(100.0);
    }

    @Test
    void assertDepositThrowsIfAmountIsZero() {
        Account account = new Account("ACC001", "Alice");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.deposit("ACC001", 0.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertDepositThrowsIfAmountIsNegative() {
        Account account = new Account("ACC001", "Alice");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.deposit("ACC001", -50.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertWithdrawDeductsAmountFromBalance() {
        Account account = new Account("ACC001", "Alice");
        account.setBalance(200.0);
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.withdraw("ACC001", 50.0);

        assertThat(result.getBalance()).isEqualTo(150.0);
    }

    @Test
    void assertWithdrawThrowsIfAmountIsZero() {
        Account account = new Account("ACC001", "Alice");
        account.setBalance(100.0);
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.withdraw("ACC001", 0.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertWithdrawThrowsIfAmountIsNegative() {
        Account account = new Account("ACC001", "Alice");
        account.setBalance(100.0);
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.withdraw("ACC001", -30.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertWithdrawThrowsIfInsufficientFunds() {
        Account account = new Account("ACC001", "Alice");
        account.setBalance(30.0);
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.withdraw("ACC001", 100.0))
                .isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void assertTransferMovesAmountBetweenAccounts() {
        Account from = new Account("ACC001", "Alice");
        from.setBalance(200.0);
        Account to = new Account("ACC002", "Bob");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("ACC002")).thenReturn(Optional.of(to));
        when(accountRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        accountService.transfer("ACC001", "ACC002", 100.0);

        assertThat(from.getBalance()).isEqualTo(100.0);
        assertThat(to.getBalance()).isEqualTo(100.0);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void assertTransferThrowsIfAmountIsZero() {
        Account from = new Account("ACC001", "Alice");
        from.setBalance(100.0);
        Account to = new Account("ACC002", "Bob");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("ACC002")).thenReturn(Optional.of(to));

        assertThatThrownBy(() -> accountService.transfer("ACC001", "ACC002", 0.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertTransferThrowsIfAmountIsNegative() {
        Account from = new Account("ACC001", "Alice");
        from.setBalance(100.0);
        Account to = new Account("ACC002", "Bob");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("ACC002")).thenReturn(Optional.of(to));

        assertThatThrownBy(() -> accountService.transfer("ACC001", "ACC002", -20.0))
                .isInstanceOf(InvalidAmountException.class);
    }

    @Test
    void assertTransferThrowsIfInsufficientFunds() {
        Account from = new Account("ACC001", "Alice");
        from.setBalance(30.0);
        Account to = new Account("ACC002", "Bob");
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("ACC002")).thenReturn(Optional.of(to));

        assertThatThrownBy(() -> accountService.transfer("ACC001", "ACC002", 100.0))
                .isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void assertTransferThrowsIfTargetAccountNotFound() {
        Account from = new Account("ACC001", "Alice");
        from.setBalance(100.0);
        when(accountRepository.findByNumber("ACC001")).thenReturn(Optional.of(from));
        when(accountRepository.findByNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.transfer("ACC001", "UNKNOWN", 50.0))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void assertTransferThrowsIfSourceAccountNotFound() {
        when(accountRepository.findByNumber("UNKNOWN")).thenReturn(Optional.empty());
        when(accountRepository.findByNumber("ACC002")).thenReturn(Optional.of(new Account("ACC002", "Bob")));

        assertThatThrownBy(() -> accountService.transfer("UNKNOWN", "ACC002", 50.0))
                .isInstanceOf(AccountNotFoundException.class);
    }
}
