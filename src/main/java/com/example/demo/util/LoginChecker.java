package com.example.demo.util;

import com.example.demo.annotation.Login;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoginChecker implements ConstraintValidator<Login, String> {

    private static final int MIN_LENGTH = 3;
    private static final String ALLOWED_SYMBOLS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789\"!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\"";

    private final Set<Character> ALLOWED_SYMBOLS_SET;

    public LoginChecker() {
        ALLOWED_SYMBOLS_SET = ALLOWED_SYMBOLS
                .chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toSet());
    }

    @Override
    public void initialize(Login constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.length() < MIN_LENGTH || s == null) {
            return false;
        }

        List<Character> loginSymbols = s.chars()
                .mapToObj(el -> (char) el)
                .toList();

        boolean notValid = loginSymbols.stream().noneMatch(ALLOWED_SYMBOLS_SET::contains);

        if (notValid) {
            return false;
        }

        return true;
    }
}
