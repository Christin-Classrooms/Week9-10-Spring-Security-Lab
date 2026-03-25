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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }

    @PostMapping("/register")
    public String registerPlayer(@Valid @ModelAttribute("player") Player player, 
                                 BindingResult result, 
                                 Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        // we're checking if the email already exists
        Player existingPlayer = playerService.findByEmail(player.getEmail());
        if (playerService.findByEmail(player.getEmail()) != null) {
            result.rejectValue("email", "error.player", "Email already registered");
            return "register";
        }
        
        // encoding the password and assigning the "PLAYER" role
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");
        
        playerService.addPlayer(player);
        
        return "redirect:/login?registered";
    }
}