package com.example.demo.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6352833693579277719L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
