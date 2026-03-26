package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String registerPlayer(@Valid @ModelAttribute("player") Player player,
                                 BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        // Every self-registered user must be a PLAYER
        player.setRole("PLAYER");

        // Password should be encoded in the service before saving
        playerService.addPlayer(player);

        return "redirect:/login";
    }
}