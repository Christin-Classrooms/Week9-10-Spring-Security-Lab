package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.model.Fighter;
import com.example.Thymeleaf.Demo.service.FighterService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateFighterController {
    private final FighterService fighterService;

    public CreateFighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    @GetMapping("/create-fighter")
    public String showCreateFighterForm(Model model) {
        model.addAttribute("fighter", new Fighter());
        return "CreateFighter";
    }

    @PostMapping("/create-fighter")
    public String createFighter(@Valid Fighter fighter, BindingResult result) {
        if(result.hasErrors()) {
            return "CreateFighter";
        }

        fighterService.addFighter(fighter);
        return "redirect:/fighters";
    }

}
