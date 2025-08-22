    package com.example.demo.controller;

    import com.example.demo.exception.UserIsBlockedException;
    import com.example.demo.exception.UserNotFoundException;
    import com.example.demo.exception.PasswordIncorrectException;
    import com.example.demo.exception.LoginIsPresentException;
    import com.example.demo.exception.EmailIsPresentException;
    import com.example.demo.exception.PasswordChangeException;
    import jakarta.validation.ConstraintViolation;
    import jakarta.validation.ConstraintViolationException;
    import org.springframework.core.Ordered;
    import org.springframework.core.annotation.Order;
    import org.springframework.http.HttpStatus;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;

    import java.time.Instant;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Set;

    @ControllerAdvice
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public class DefaultExceptionHandler {

        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleValidationException(ConstraintViolationException ex, Model model) {

            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();

            List<String> messages = new ArrayList<>();
            for (ConstraintViolation<?> violation : errors){
                messages.add(violation.getMessage());
            }

            model.addAttribute("messages", messages);
            model.addAttribute("message", "Текущие ошибки синтаксиса:");
            model.addAttribute("type", "Ошибка синтаксиса!");

            return "info";
        }

        @ExceptionHandler(EmailIsPresentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleEmailIsPresentException(EmailIsPresentException ex, Model model) {
            model.addAttribute("type", "Аккаунт существует");
            model.addAttribute("message", ex.getMessage());

            return "info";
        }

        @ExceptionHandler(LoginIsPresentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleLoginIsPresentException(LoginIsPresentException ex, Model model) {
            model.addAttribute("type", "Аккаунт существует");
            model.addAttribute("message", ex.getMessage());

            return "info";
        }

        @ExceptionHandler(PasswordIncorrectException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handlePasswordIncorrectException(PasswordIncorrectException ex, Model model) {
            model.addAttribute("type", "Ошибка");
            model.addAttribute("text", ex.getMessage());

            return "wrongAuthorization";
        }

        @ExceptionHandler(UserNotFoundException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
            model.addAttribute("type", "Ошибка");
            model.addAttribute("text", ex.getMessage());

            return "wrongAuthorization";
        }

        @ExceptionHandler(UserIsBlockedException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleUserIsBlockedException(UserIsBlockedException ex, Model model) {
            model.addAttribute("type", "Пользователь заблокирован");
            model.addAttribute("text", ex.getMessage());

            return "wrongAuthorization";
        }

        @ExceptionHandler(PasswordChangeException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handlePasswordChangeException(PasswordChangeException ex, Model model) {
            model.addAttribute("type", "Ошибка");
            model.addAttribute("message", ex.getMessage());
            model.addAttribute("login", ex.getLogin());

            return "changePassword";
        }


        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public String handleNotFound(Exception ex, Model model) {
            model.addAttribute("title", "Неизвестная ошибка");
            model.addAttribute("text", ex.getMessage());
            model.addAttribute("time", Instant.now());

            return "UnknownError";
        }
    }