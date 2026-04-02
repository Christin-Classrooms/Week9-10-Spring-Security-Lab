package com.example.Thymeleaf.Demo.controllers;


import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final PlayerService playerService;

    public RegisterController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model ){

        model.addAttribute("player", new Player());
        return "register";

    }

    @PostMapping("/register")
    public String register(@Valid Player player, BindingResult result){

        if(result.hasErrors()){
            return "register";
        }

        playerService.addPlayer(player);
        return "redirect:/login";
    }








}
