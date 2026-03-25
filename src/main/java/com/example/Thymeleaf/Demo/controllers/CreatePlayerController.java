package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class CreatePlayerController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlayerRepository playerRepo;

    @GetMapping
    public String getRegistrationPage(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @PostMapping
    public String registerPlayer(@ModelAttribute("player") Player player) {

        if (playerRepo.findByName(player.getName()).isPresent()) {
            return "redirect:/register?error";
        }

        player.setName(player.getName());
        player.setEmail(player.getEmail());
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");

        playerRepo.save(player);

        return "redirect:/login";
    }
}
