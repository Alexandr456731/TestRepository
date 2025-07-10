package com.example.demo.service;

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

        userRepository.save(user);
        return "Успешно!";
    }

    public User authorization(String login, String password){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        User user;
        if (optionalUser != null && !optionalUser.isEmpty()) {
            user = optionalUser.get();
        }else {
            return null;
        }

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }else{
            return null;
        }
    }

    public String delete(String login, String password){
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user;
        if (!optionalUser.isEmpty() && optionalUser != null) {
            user = optionalUser.get();
        }else{
            return "Пользователь не найден!";
        }

        if (user == null) {
            return "Пользователь не найден!";
        }

        if (user.getPassword().equals(password)) {
            userRepository.delete(user);
            return "Успешно!";
        }else{
            return "Пароль пользователя неверный!";
        }
    }

    public String changePassword(String login, String oldPassword, String newPassword, String confirmPassword){
        Optional<User> optionalUser = userRepository.findByLogin(login);

        User user;
        if (!optionalUser.isEmpty() && optionalUser != null) {
            user = optionalUser.get();
        }else{
            return "Пользователь не найден!";
        }

        if (user == null) {
            return "Пользователь не найден!";
        }

        if (!user.getPassword().equals(oldPassword)) {
            return "Старый пароль неверный пользователя неверный!";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "Пароли не совподают!";
        }

        user.setPassword(newPassword);

        userRepository.save(user);

        return "Успешно!";
    }
}
