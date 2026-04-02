package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.FighterRepository;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Battle feature.
 * Connects to the external battle service through RestTemplate.
 * User logic depends on session stored 'loggedInPlayer'.
 */
@Controller
public class BattleController {

    private final FighterRepository fighterRepository;
    private final PlayerRepository playerRepository;
    private final RestTemplate restTemplate;

    public BattleController(FighterRepository fighterRepository, 
                            PlayerRepository playerRepository,
                            RestTemplate restTemplate) {
        this.fighterRepository = fighterRepository;
        this.playerRepository = playerRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/battle")
    public String showBattleForm(HttpSession session, Principal principal, Model model) {
        model.addAttribute("fighters", fighterRepository.findAll());
        
        // Ensure session is populated from authentication
        Player loggedInPlayer = (Player) session.getAttribute("loggedInPlayer");
        if (loggedInPlayer == null && principal != null) {
            loggedInPlayer = playerRepository.findByName(principal.getName());
            session.setAttribute("loggedInPlayer", loggedInPlayer);
        }
        
        model.addAttribute("loggedInPlayer", loggedInPlayer);
        return "battle";
    }

    @PostMapping("/battle")
    public String initiateBattle(@RequestParam int fighter1Id,
                                 @RequestParam int fighter2Id,
                                 HttpSession session,
                                 Principal principal,
                                 Model model) {
        
        // Retrieve the logged-in player from session as specified
        Player loggedInPlayer = (Player) session.getAttribute("loggedInPlayer");
        
        // If missing from session, fetch from DB using the Spring Security Principal
        if (loggedInPlayer == null && principal != null) {
            loggedInPlayer = playerRepository.findByName(principal.getName());
            session.setAttribute("loggedInPlayer", loggedInPlayer);
        }

        if (loggedInPlayer == null) {
            return "redirect:/login";
        }

        // Prepare JSON body for battle service
        Map<String, Object> request = new HashMap<>();
        request.put("fighter1Id", fighter1Id);
        request.put("fighter2Id", fighter2Id);
        request.put("playerId", loggedInPlayer.getId());
        request.put("playerName", loggedInPlayer.getName());

        try {
            // Make the HTTP POST to localhost:8081/battles for standalone local testing
            String url = "http://localhost:8081/battles";
            @SuppressWarnings("unchecked")
            Map<String, Object> battleResult = restTemplate.postForObject(url, request, Map.class);

            model.addAttribute("battle", battleResult);
            model.addAttribute("loggedInPlayer", loggedInPlayer);

            return "battle-result";
        } catch (Exception e) {
            // Handle cases where the battle service is unreachable
            model.addAttribute("error", "The battle service is currently unavailable. Please try again later.");
            return "battle";
        }
    }
}
