package com.example.demo.exception;

import java.io.Serial;

public class EmailIsPresentException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7464608480633301924L;

    public EmailIsPresentException(String message) {
        super(message);
    }
}
