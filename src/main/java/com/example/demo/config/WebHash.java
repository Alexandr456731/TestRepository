package com.example.demo.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class WebHash {
    private final UserRepository userRepository;

    public WebHash(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/hash-admin/{login}/{hash}")
    public String getHash(@PathVariable String login, @PathVariable String hash) {
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
