package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.Map;
import java.util.function.Function;

public class HashConfiguration {
    public static void setHasher(Map<String, Function<User, String>> hasher, UserRepository userRepository) {
        hasher.clear();

        hasher.put("freemoney", user -> {
            user.setIsBlocked(true);
            userRepository.save(user);
            return "Вы ввели хэш блокировки или хэш который был пиратский, поэтому ваш аккаунт будет заблокирован!";
        });

        hasher.put("firstmoney", user -> {
            user.addBalance(100);
            userRepository.save(user);
            return "Поздравляем, вам начисленно 100 монет!";
        });
    }
}
