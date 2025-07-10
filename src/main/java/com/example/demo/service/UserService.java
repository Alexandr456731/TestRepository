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

        if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 8) {
            return "Пароль слишком короткий!";
        }

        String[] mass = user.getPassword().split(" ");
        if (mass.length > 1) {
            return "Пароль не может содержать пробелы!";
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

        if (user.getIsBlocked()){
            return "Данный пользователь заблокирован!";
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

        if (user.getIsBlocked()){
            return "Данный пользователь заблокирован!";
        }

        if (!user.getPassword().equals(oldPassword)) {
            return "Старый пароль неверный пользователя неверный!";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "Пароли не совподают!";
        }

        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 8) {
            return "Пароль слишком короткий!";
        }

        String[] mass = newPassword.split(" ");
        if (mass.length > 1) {
            return "Пароль не может содержать пробелы!";
        }

        user.setPassword(newPassword);

        userRepository.save(user);

        return "Успешно!";
    }
}
