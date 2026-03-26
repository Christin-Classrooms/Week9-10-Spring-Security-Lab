package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CreatePlayerController {

    private final PlayerService playerService;

    public CreatePlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("player") Player player) {
        playerService.addPlayer(player);
        return "redirect:/login?success";
    }
}