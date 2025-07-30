package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.Checker;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registration(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return "Email не может быть пустым!";
        }

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            return "Логин не может быть пустым!";
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        if (byEmail.isPresent()) {
            return "Аккаунт с таким email уже существует!";
        }

        Optional<User> byLogin = userRepository.findByLogin(user.getLogin());

        if (byLogin.isPresent()) {
            return "Аккаунт с таким логином уже существует!";
        }


        String resForCheck = Checker.checkPassword(user.getPassword());
        if (resForCheck != null){
            return resForCheck;
        }

        userRepository.save(user);
        return "Успешно!";
    }

    public User authorization(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user;
        if (optionalUser != null && !optionalUser.isEmpty()) {
            user = optionalUser.get();
        } else {
            return null;
        }

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public String delete(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user;
        if (!optionalUser.isEmpty() && optionalUser != null) {
            user = optionalUser.get();
        } else {
            return "Пользователь не найден!";
        }

        if (user == null) {
            return "Пользователь не найден!";
        }

        if (user.getIsBlocked()) {
            return "Данный пользователь заблокирован!";
        }

        if (user.getPassword().equals(password)) {
            userRepository.delete(user);
            return "Успешно!";
        } else {
            return "Пароль пользователя неверный!";
        }
    }

    public String changePassword(String login, String oldPassword, String newPassword, String confirmPassword) throws UserPrincipalNotFoundException {
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user = userRepository.findByLogin(login).orElseThrow(() -> new UserPrincipalNotFoundException("User not found error"));


        if (user.getIsBlocked()) {
            throw new UserPrincipalNotFoundException("Пользователь был заблокирован!");
        }

        if (!user.getPassword().equals(oldPassword)) {
            throw new UserPrincipalNotFoundException("Старый пароль неверный!");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new UserPrincipalNotFoundException("Пароли не совподают!");
        }

        String resCheck = Checker.checkPassword(newPassword);
        if (resCheck != null) {
            throw new UserPrincipalNotFoundException(resCheck);
        }

        user.setPassword(newPassword);

        userRepository.save(user);

        return "Успешно!";
    }
}
