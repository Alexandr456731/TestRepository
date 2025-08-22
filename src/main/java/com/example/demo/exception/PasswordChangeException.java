package com.example.demo.exception;

import java.io.Serial;

public class PasswordChangeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4332480946007177007L;
    private final String login;

    public String getLogin() {
        return login;
    }

    public PasswordChangeException(String message, String login) {
        super(message);
        this.login = login;
    }
}
