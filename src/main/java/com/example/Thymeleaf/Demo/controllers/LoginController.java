package com.example.Thymeleaf.Demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// self-note: login controller
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}