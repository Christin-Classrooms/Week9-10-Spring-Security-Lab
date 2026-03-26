package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayersController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public String showPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String search,
            Model model) {

        // Get actual paged data from the service
        Page<Player> playerPage = playerService.getPlayersPaged(page, size, sort, direction);

        // Map data to the attributes your template expects
        model.addAttribute("players", playerPage.getContent());
        model.addAttribute("total", playerPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", playerPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("search", search);
        
        // Pagination logic for buttons
        model.addAttribute("hasPrevious", playerPage.hasPrevious());
        model.addAttribute("hasNext", playerPage.hasNext());
        
        // Calculate the "Showing X to Y" info
        int start = page * size + 1;
        long end = Math.min(start + size - 1, playerPage.getTotalElements());
        model.addAttribute("startIndex", start);
        model.addAttribute("endIndex", end);

        return "players";
    }
}