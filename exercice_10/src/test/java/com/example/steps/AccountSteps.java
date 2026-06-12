package com.example.steps;

import com.example.exception.DuplicateAccountException;
import com.example.exception.InvalidCredentialsException;
import com.example.model.LoginResult;
import com.example.model.RegistrationConfirmation;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.AccountService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountSteps {

    private UserRepository userRepository;
    private AccountService accountService;

    private RegistrationConfirmation registration;
    private LoginResult loginResult;
    private RuntimeException error;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        accountService = new AccountService(userRepository);
        registration = null;
        loginResult = null;
        error = null;
    }

    @Given("no account exists with username {string}")
    public void noAccountExistsWithUsername(String username) {
        when(userRepository.existsByUsername(username)).thenReturn(false);
    }

    @Given("an account already exists with username {string}")
    public void anAccountAlreadyExistsWithUsername(String username) {
        when(userRepository.existsByUsername(username)).thenReturn(true);
    }

    @Given("an account already exists with email {string}")
    public void anAccountAlreadyExistsWithEmail(String email) {
        when(userRepository.existsByEmail(email)).thenReturn(true);
    }

    @When("the user registers with email {string}, username {string} and password {string}")
    public void theUserRegisters(String email, String username, String password) {
        try {
            registration = accountService.register(email, username, password);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @Then("the registration is confirmed for {string}")
    public void theRegistrationIsConfirmedFor(String username) {
        assertNull(error, "Registration should have succeeded");
        assertNotNull(registration, "A registration confirmation should have been returned");
        assertEquals(username, registration.getUsername());
        assertFalse(registration.getMessage().isBlank(), "The confirmation message should not be blank");

        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertEquals(username, savedUser.getValue().getUsername());
        assertNotNull(savedUser.getValue().getEmail(), "The saved account should keep the email");
    }

    @Then("the registration is rejected because the account already exists")
    public void theRegistrationIsRejected() {
        assertNotNull(error, "Registration should have been rejected");
        assertInstanceOf(DuplicateAccountException.class, error);
        assertNull(registration);
    }

    @Given("a registered user {string} with password {string}")
    public void aRegisteredUser(String username, String password) {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User(username + "@shop.com", username, password)));
    }

    @Given("no registered user with username {string}")
    public void noRegisteredUserWithUsername(String username) {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    }

    @When("{string} logs in with password {string}")
    public void logsInWithPassword(String username, String password) {
        try {
            loginResult = accountService.login(username, password);
        } catch (RuntimeException e) {
            error = e;
        }
    }

    @Then("the login succeeds and redirects to the {string} page")
    public void theLoginSucceedsAndRedirects(String page) {
        assertNull(error, "Login should have succeeded");
        assertNotNull(loginResult, "A login result should have been returned");
        assertEquals(page, loginResult.getRedirectPage());
        assertFalse(loginResult.getMessage().isBlank(), "The welcome message should not be blank");
    }

    @Then("the login fails with an error message")
    public void theLoginFails() {
        assertNotNull(error, "Login should have failed");
        assertInstanceOf(InvalidCredentialsException.class, error);
        assertNull(loginResult);
    }
}
