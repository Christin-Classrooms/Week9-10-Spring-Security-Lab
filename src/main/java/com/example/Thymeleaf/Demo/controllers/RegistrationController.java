package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerDetailsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final PlayerDetailsService playerService;

    public RegistrationController(PlayerDetailsService playerService) {
        this.playerService = playerService;
    }

    // Registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    // Registration submission
    @PostMapping("/register")
    public String registerPlayer(@Valid Player player, BindingResult result) {

        if (result.hasErrors()) {return "register";}

        // Set default role for new users
        player.setRole("PLAYER");
        playerService.addPlayer(player);

        return "redirect:/login";
    }
}

