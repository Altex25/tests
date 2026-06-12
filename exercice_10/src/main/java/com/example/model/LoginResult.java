package com.example.model;

public class LoginResult {

    private final String redirectPage;
    private final String message;

    public LoginResult(String redirectPage, String message) {
        this.redirectPage = redirectPage;
        this.message = message;
    }

    public String getRedirectPage() {
        return redirectPage;
    }

    public String getMessage() {
        return message;
    }
}
