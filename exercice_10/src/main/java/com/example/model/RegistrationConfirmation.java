package com.example.model;

public class RegistrationConfirmation {

    private final String username;
    private final String message;

    public RegistrationConfirmation(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
