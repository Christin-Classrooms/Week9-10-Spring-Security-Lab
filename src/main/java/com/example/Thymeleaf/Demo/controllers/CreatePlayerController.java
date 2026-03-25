package com.example.Thymeleaf.Demo.controllers;


import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class CreatePlayerController {
    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;

    public CreatePlayerController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showCreatePlayerForm(Model model ){

        model.addAttribute("player",   new Player());
        return "register";

    }


    @PostMapping("/register")
    public String createPlayer(@Valid Player player, BindingResult result){

        if(result.hasErrors()){
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);

        player.setRole("PLAYER");

        playerService.addPlayer(player);
        return "redirect:/login";
    }


}