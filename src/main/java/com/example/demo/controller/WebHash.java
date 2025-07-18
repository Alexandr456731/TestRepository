package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import com.example.demo.service.WebHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHash {
    private final WebHashService webHashService;

    public WebHash(WebHashService webHashService){
        this.webHashService = webHashService;
    }

    @GetMapping("/hash-admin/{login}/{hash}")
    public String getHash(@PathVariable String login, @PathVariable String hash) {
        return webHashService.getHash(login, hash);
    }
}
