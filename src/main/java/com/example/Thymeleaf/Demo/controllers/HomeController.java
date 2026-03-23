package com.example.Thymeleaf.Demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;

@Controller
public class HomeController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("message", "Hello From Cpan 228 Java Code sent the text");
        return "Home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        try {
            Player existingPlayer = playerService.findByUsername(username);
            if (existingPlayer != null) {
                model.addAttribute("error", "Username already exists!");
                return "register";
            }
            
            Player newPlayer = new Player();
            newPlayer.setUsername(username);
            newPlayer.setName(name);
            newPlayer.setEmail(email);
            newPlayer.setPassword(passwordEncoder.encode(password));
            newPlayer.setRole("PLAYER");
            
            playerService.savePlayer(newPlayer);
            
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }
}