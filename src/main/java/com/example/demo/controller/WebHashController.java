package com.example.demo.controller;

import com.example.demo.service.WebHashService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebHashController {
    private final WebHashService webHash;

    public WebHashController(WebHashService webHash){
        this.webHash = webHash;
    }

    @GetMapping("/hash-admin/{login}/{hash}")
    public String getHash(@PathVariable String login, @PathVariable String hash, Model model) {
        model.addAttribute("message", webHash.getHash(login, hash));
        return "info";
    }
}
