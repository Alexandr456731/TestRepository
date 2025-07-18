package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class WebHashService {
    private final UserRepository userRepository;

    public WebHashService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String getHash( String login,  String hash) {
        List<String> hashList = List.of();
        List<String> blockerList = List.of("hash-4018");
        List<String> adminList = List.of();

        User user;
        Optional<User> ou = userRepository.findByLogin(login);
        if (ou.isPresent()) {
            user = ou.get();
        }else{
            return "Пользователь с таким именем не найден!";
        }

        for (String el : hashList) {
            if (el.equals(hash)) {
                return "Просто хэш, который пока что ничего не делает!";
            }
        }

        for (String el : blockerList) {
            if (el.equals(hash)) {
                user.setIsBlocked(true);
                userRepository.save(user);
                return "Вы ввели хэш блокировки или хэш который был пиратский, поэтому ваш аккаунт будет заблокирован!";
            }
        }

        for (String el : adminList) {
            if (el.equals(hash)) {
                user.setIsAdmin(true);
                userRepository.save(user);
                return "Вы ввели админ-хэш, поэьому вы получаете админку!";
            }
        }

        return "Хэш недействительный!";
    }
}