package com.example.demo.annotation;

import com.example.demo.util.PasswordChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = PasswordChecker.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPassword {
    String message() default "Пароль некорректный!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

