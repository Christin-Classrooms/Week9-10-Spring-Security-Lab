package com.example.Thymeleaf.Demo.controllers;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Controller
public class RegisterController {

    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;

    public RegisterController(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegForm(Model model) {
        model.addAttribute("player", new Player());
        return "Register";
    }

    @PostMapping("/register")
    public String registerPlayer(Player player) {

        String encodedPassword = passwordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);

        player.setRole("PLAYER");

        playerRepository.save(player);
        return "redirect:/login";
    }
}
