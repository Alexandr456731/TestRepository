package com.example.demo.service;

import com.example.demo.exception.*;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registration(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        if (byEmail.isPresent()) {
            throw new EmailIsPresentException("Аккаунт с таким email уже существует!");
        }

        Optional<User> byLogin = userRepository.findByLogin(user.getLogin());

        if (byLogin.isPresent()) {
            throw new LoginIsPresentException("Аккаунт с таким логином уже существует!");
        }

        userRepository.save(user);
    }

    public User authorization(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user;
        if (optionalUser != null && !optionalUser.isEmpty()) {
            user = optionalUser.get();
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        if (user.getIsBlocked()){
            throw new UserIsBlockedException("Пользоваатель заблокирован!");
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new PasswordIncorrectException("Пароль неверный!");
        }
    }

    public void delete(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user;
        if (!optionalUser.isEmpty() && optionalUser != null) {
            user = optionalUser.get();
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }

        if (user.getIsBlocked()) {
            throw new UserIsBlockedException("Данный пользователь заблокирован!");
        }

        if (user.getPassword().equals(password)) {
            userRepository.delete(user);
        } else {
            throw new PasswordIncorrectException("Пароль неверный!");
        }
    }

    public void changePassword(String login, String oldPassword, String newPassword, String confirmPassword) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("Ошибка: пользователь не существует"));


        if (user.getIsBlocked()) {
            throw new UserIsBlockedException("Пользователь был заблокирован!");
        }

        if (!user.getPassword().equals(oldPassword)) {
            throw new PasswordChangeException("Старый пароль неверный!", login);
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordChangeException("Пароли не совподают!", login);
        }

        user.setPassword(newPassword);

        userRepository.save(user);
    }
}
