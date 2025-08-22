package com.example.demo.exception;

import java.io.Serial;

public class LoginIsPresentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4656186494466677816L;

    public LoginIsPresentException(String message) {
        super(message);
    }
}
