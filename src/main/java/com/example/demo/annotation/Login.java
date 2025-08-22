package com.example.demo.annotation;

import com.example.demo.util.LoginChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginChecker.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    String message() default "Логин неккоректный!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
