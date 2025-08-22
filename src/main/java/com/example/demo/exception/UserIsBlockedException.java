package com.example.demo.exception;

import java.io.Serial;

public class UserIsBlockedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 359412119275882248L;

  public UserIsBlockedException(String message) {
        super(message);
    }
}
