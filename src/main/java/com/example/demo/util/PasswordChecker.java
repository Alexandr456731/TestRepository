package com.example.demo.util;

import com.example.demo.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PasswordChecker implements ConstraintValidator<Password, String> {
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
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() < MIN_LENGTH) {
            return false;
        }

        List<Character> symbols = s.chars()
                .mapToObj(el -> (char) el)
                .toList();

        if (!(Collections.disjoint(symbols, BIG_LETTERS_SET) && Collections.disjoint(symbols, SMALL_LETTERS_SET))) {
            return false;
        }


        if (!Collections.disjoint(symbols, DIGITS_SET)) {
            return false;
        }

        Set<Character> availableSymbols = new LinkedHashSet<>(DIGITS_SET);
        availableSymbols.addAll(BIG_LETTERS_SET);
        availableSymbols.addAll(SMALL_LETTERS_SET);
        availableSymbols.addAll(SPEC_SYMBOLS_SET);

        boolean notValid = symbols.stream().noneMatch(availableSymbols::contains);

        if (notValid) {
            return false;
        }

        return true;
    }
}
