package com.example.Thymeleaf.Demo.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class CreatePlayerController {

    private final PlayerService playerService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreatePlayerController(PlayerService playerService,
                                  BCryptPasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    // SHOW REGISTER PAGE
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "Register-CreatePlayer";
    }

    // HANDLE REGISTER
    @PostMapping("/register")
    public String registerPlayer(@Valid @ModelAttribute("player") Player player,
                                 BindingResult result) {

        // DEBUG (temporary)
        System.out.println("REGISTER HIT: " + player.getEmail());

        if (result.hasErrors()) {
            return "Register-CreatePlayer";
        }

        // encode password
        player.setPassword(passwordEncoder.encode(player.getPassword()));

        // assign role
        player.setRole("USER");

        playerService.addPlayer(player);

        return "redirect:/login";
    }
}