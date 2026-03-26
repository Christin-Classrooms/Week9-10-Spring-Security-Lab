package com.example.Thymeleaf.Demo.controllers;


import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreatePlayerController {
    private final PlayerService playerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CreatePlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/create-player")
    public String showCreatePlayerForm(Model model ){

        model.addAttribute("player",   new Player());
        return "register";

    }


    @PostMapping("/create-player")
    public String createPlayer(@Valid Player player, BindingResult result){

        if(result.hasErrors()){
            return "register";
        }

        // Encode the password
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        
        // Assign PLAYER role to all self-registered users
        player.setRole("PLAYER");

        playerService.addPlayer(player);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model ){

        model.addAttribute("player",   new Player());
        return "register";

    }


    @PostMapping("/register")
    public String registerPlayer(@Valid Player player, BindingResult result){

        if(result.hasErrors()){
            return "register";
        }

        // Encode the password
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        
        // Assign PLAYER role to all self-registered users
        player.setRole("PLAYER");

        playerService.addPlayer(player);
        return "redirect:/login";
    }








}
