package com.example.demo.exception;

import java.io.Serial;

public class PasswordException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8101985891635649443L;

    public PasswordException(String message) {
        super(message);
    }
}
