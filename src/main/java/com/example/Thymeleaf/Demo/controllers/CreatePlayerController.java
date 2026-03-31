package com.example.Thymeleaf.Demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public CreatePlayerController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/create-player", "/register"})
    public String showCreatePlayerForm(Model model) {
        model.addAttribute("player", new Player());
        return "CreatePlayer";
    }

    @PostMapping({"/create-player", "/register"})
    public String createPlayer(@Valid @ModelAttribute Player player, BindingResult result) {
        if (result.hasErrors()) {
            return "CreatePlayer";
        }

        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");
        playerService.addPlayer(player);
        return "redirect:/players";
    }
}
