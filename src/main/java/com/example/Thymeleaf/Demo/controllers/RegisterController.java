package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping
    public String registerPlayer(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String password) {

        Player player = new Player();
        player.setName(name);
        player.setEmail(email);
        player.setPassword(passwordEncoder.encode(password));
        player.setRole("PLAYER");

        playerRepository.save(player);

        return "redirect:/login";
    }
}