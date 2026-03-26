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

    // Part 5: Serve the login page (Spring Security handles POST /login automatically)
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
