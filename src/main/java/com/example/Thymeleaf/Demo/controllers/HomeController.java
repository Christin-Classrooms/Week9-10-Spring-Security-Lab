package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomePage(Model model){

        model.addAttribute("message", "Hello From Cpan 228 Java Code sent the text");
        return "Home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Player player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");
        playerRepository.save(player);
        return "redirect:/login";
    }

}
