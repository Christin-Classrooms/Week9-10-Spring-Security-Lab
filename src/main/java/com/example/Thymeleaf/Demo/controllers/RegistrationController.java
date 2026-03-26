package com.example.Thymeleaf.Demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private final PlayerService playerService;

    @Autowired
    public RegistrationController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("player") Player player, 
                               BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        
        playerService.addPlayer(player); 
        
        return "redirect:/login?success";
    }
}