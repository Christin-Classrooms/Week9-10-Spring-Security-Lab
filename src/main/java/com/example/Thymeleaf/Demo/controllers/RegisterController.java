package com.example.Thymeleaf.Demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlayerRepository repo;

    @GetMapping()
    public String showCreatePlayerForm(Model model ){
        model.addAttribute("player", new Player());
        return "Register";
    }


    @PostMapping
    public String registerUserToDB(@RequestParam  String username, @RequestParam String password, @RequestParam String email){
        //check if user exists in DB

        if(repo.findByUsername(username).isPresent()){

            return "redirect:register?error";

        }

        Player user = new Player();

        user.setUsername(username);
        String encodedPass = passwordEncoder.encode(password);
        user.setPassword(encodedPass);
        user.setEmail(email);

        repo.save(user);

        return "redirect:/login";
    }


}
