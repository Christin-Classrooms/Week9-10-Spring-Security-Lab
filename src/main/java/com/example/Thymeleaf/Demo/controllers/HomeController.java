package com.example.Thymeleaf.Demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Model model){

        model.addAttribute("message", "Hello From Cpan 228 Java Code sent the text");
        return "Home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
