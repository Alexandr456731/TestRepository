package com.example.demo.service;

import com.example.demo.config.HashConfiguration;
import com.example.demo.exception.UserIsBlockedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Service
public class WebHashService {
    private final UserRepository userRepository;
    private final Map<String, Function<User, String>> hasher;

    public WebHashService(UserRepository userRepository){
        this.userRepository = userRepository;
        hasher = new HashMap<>();

        HashConfiguration.setHasher(hasher, this.userRepository);
    }

    public String getHash(String login,  String hash) {

        User user;
        Optional<User> ou = userRepository.findByLogin(login);
        if (ou.isPresent()) {
            user = ou.get();
        }else{
            return "Пользователь с таким именем не найден!";
        }

        for (String key : hasher.keySet()) {
            if (hash.equals(key)){
                return hasher.get(key).apply(user);
            }
        }


        return "Хэш недействительный!";
    }
}