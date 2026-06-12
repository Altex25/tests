package com.example.service;

import com.example.exception.DuplicateAccountException;
import com.example.exception.InvalidCredentialsException;
import com.example.model.LoginResult;
import com.example.model.RegistrationConfirmation;
import com.example.model.User;
import com.example.repository.UserRepository;

public class AccountService {

    private final UserRepository userRepository;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationConfirmation register(String email, String username, String password) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new DuplicateAccountException("An account already exists for " + username);
        }
        userRepository.save(new User(email, username, password));
        return new RegistrationConfirmation(username, "Account created for " + username);
    }

    public LoginResult login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return new LoginResult("home", "Welcome " + username);
    }
}
