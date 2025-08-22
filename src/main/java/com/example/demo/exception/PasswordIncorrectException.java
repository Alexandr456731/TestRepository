package com.example.demo.exception;

import java.io.Serial;

public class PasswordIncorrectException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8056425113991471236L;

    public PasswordIncorrectException(String message) {
        super(message);
    }
}
