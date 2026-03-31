package com.example.Thymeleaf.Demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class AuthController {
    
    @GetMapping("/login")
public String login() {
    return "login";
}
}
