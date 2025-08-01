package com.example.demo.util;

import com.example.demo.annotation.CheckPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;

public class PasswordChecker implements ConstraintValidator<CheckPassword, String> {
    private static final int MIN_LENGTH = 8;
    private static final String BIG_LETTERS = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String SMALL_LETTERS = "qwertyuiopasdfghjklzxcvbnm";
    private static final String DIGITS = "0123456789";
    private static final String SPEC_SYMBOLS = "\"!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\"";

    private final Set<Character> BIG_LETTERS_SET;
    private final Set<Character> SMALL_LETTERS_SET;
    private final Set<Character> DIGITS_SET;
    private final Set<Character> SPEC_SYMBOLS_SET;

    public PasswordChecker() {
        BIG_LETTERS_SET = BIG_LETTERS
                .chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toSet());

        SMALL_LETTERS_SET = SMALL_LETTERS
                .chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toSet());

        DIGITS_SET = DIGITS
                .chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toSet());

        SPEC_SYMBOLS_SET = SPEC_SYMBOLS
                .chars()
                .mapToObj(el -> (char) el)
                .collect(Collectors.toSet());
    }

    @Override
    public void initialize(CheckPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
