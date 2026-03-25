package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class CreatePlayerController {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreatePlayerController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("player", new Player());
        return "CreatePlayer";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("player") Player player,
                                      BindingResult result,
                                      Model model) {

        if (playerRepository.findByEmail(player.getEmail()).isPresent()) {
            result.rejectValue("email", "error.player", "Email is already registered");
        }

        if (result.hasErrors()) {
            return "CreatePlayer";
        }

        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");

        playerRepository.save(player);

        return "redirect:/login";
    }
}