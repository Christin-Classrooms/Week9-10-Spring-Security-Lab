package com.example.Thymeleaf.Demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;

import jakarta.validation.Valid;

// REGISTRATON CONTROLLER REFACTORED FROM CREATE-PLAYER
@Controller
public class CreatePlayerController {

    private final PlayerService playerService;

    // ENCODER INJECTED BCRYPT ALL THE PASSWORDS
    private final PasswordEncoder passwordEncoder;

    public CreatePlayerController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    // SHOW REGISTER FORM
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    // HANDLE REGISTER SUBMIT
    @PostMapping("/register")
    public String register(@Valid Player player,
            BindingResult result,
            @RequestParam("password") String rawPassword) {

        if (result.hasErrors()) {
            return "register";
        }

        // ENCODE BEFORE SAVE NEVER STORE RAW PASSWORD
        player.setPassword(passwordEncoder.encode(rawPassword));

        // EVERYONE WHO SELF-REGISTERS IS A PLAYER
        player.setRole("PLAYER");

        playerService.addPlayer(player);

        // SEND THEM TO LOGIN AFTER REGISTRATON
        return "redirect:/login";
    }
}
