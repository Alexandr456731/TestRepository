package com.example.demo.annotation;

import com.example.demo.util.PasswordChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = PasswordChecker.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Пароль некорректный!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

