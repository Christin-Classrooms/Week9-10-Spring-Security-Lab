package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayersController {

    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public String getPlayers(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction,
            Model model) {

        // 1. Handle Sorting logic
        Sort.Direction sortedDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortedDirection, sortBy);

        // 2. Create Pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Fetch Data
        Page<Player> playerPage;
        if (search != null && !search.trim().isEmpty()) {
            playerPage = playerService.findPlayerByName(search, pageable);
        } else {
            playerPage = playerService.getAllPlayersPageable(pageable);
        }

        // 4. Map Attributes to Thymeleaf
        model.addAttribute("players", playerPage.getContent());
        model.addAttribute("total", playerPage.getTotalElements()); // Matches ${total} in Players.html
        model.addAttribute("size", size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", playerPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("sort", sortBy);
        model.addAttribute("direction", direction);

        // 5. Pagination UI Helpers
        model.addAttribute("hasPrevious", playerPage.hasPrevious());
        model.addAttribute("hasNext", playerPage.hasNext());

        // Logic for "Showing X to Y of Z"
        int startIndex = (page * size) + 1;
        // Ensure endIndex doesn't exceed total elements
        long endIndex = Math.min((long) (page + 1) * size, playerPage.getTotalElements());

        model.addAttribute("startIndex", playerPage.getTotalElements() == 0 ? 0 : startIndex);
        model.addAttribute("endIndex", endIndex);

        return "Players";
    }
}