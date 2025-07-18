package com.example.demo.controller;

import com.example.demo.service.WebHashService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHashController {
    private final WebHashService webHash;

    public WebHashController(WebHashService webHash){
        this.webHash = webHash;
    }

    @GetMapping("/hash-admin/{login}/{hash}")
    public String getHash(@PathVariable String login, @PathVariable String hash) {
        return webHash.getHash(login, hash);
    }
}
