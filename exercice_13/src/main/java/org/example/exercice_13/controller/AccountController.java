package org.example.exercice_13.controller;

import org.example.exercice_13.dto.AmountRequest;
import org.example.exercice_13.dto.CreateAccountRequest;
import org.example.exercice_13.dto.TransferRequest;
import org.example.exercice_13.model.Account;
import org.example.exercice_13.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody CreateAccountRequest request) {
        Account created = accountService.createAccount(request.number(), request.owner());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{number}")
    public ResponseEntity<Account> getByNumber(@PathVariable String number) {
        return ResponseEntity.ok(accountService.getAccount(number));
    }

    @PostMapping("/{number}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String number, @RequestBody AmountRequest request) {
        return ResponseEntity.ok(accountService.deposit(number, request.amount()));
    }

    @PostMapping("/{number}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable String number, @RequestBody AmountRequest request) {
        return ResponseEntity.ok(accountService.withdraw(number, request.amount()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest request) {
        accountService.transfer(request.from(), request.to(), request.amount());
        return ResponseEntity.ok().build();
    }
}
