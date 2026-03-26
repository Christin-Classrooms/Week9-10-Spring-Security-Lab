package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Fighter;
import com.example.Thymeleaf.Demo.repository.FighterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FighterService {

    private final FighterRepository fighterRepository;

    public FighterService(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    /**
     * Handles the complex logic for filtering and searching fighters
     * based on the criteria passed from the UI.
     */
    public Page<Fighter> getFighters(Pageable pageable, String search, String filterType) {
        if (filterType == null) filterType = "all";

        switch (filterType.toLowerCase()) {
            case "name":
                return fighterRepository.findByNameContainingIgnoreCase(search != null ? search : "", pageable);
            case "health":
                int health = 0;
                try {
                    health = (search != null && !search.isEmpty()) ? Integer.parseInt(search) : 0;
                } catch (NumberFormatException e) {
                    health = 0;
                }
                return fighterRepository.findByHealthGreaterThan(health, pageable);
            case "strongest":
                return fighterRepository.findStrongestFighters(pageable);
            case "balanced":
                return fighterRepository.findBalancedFighters(1200, 150, pageable);
            default:
                return fighterRepository.findAll(pageable);
        }
    }

    /**
     * Saves a new fighter to the H2 database.
     * This fixes the UnsupportedOperationException error.
     */
    public void addFighter(Fighter fighter) {
        // We call the built-in save method from JpaRepository
        fighterRepository.save(fighter);

        // Optional: Logging to the console to confirm the save worked
        System.out.println("DEBUG: Fighter '" + fighter.getName() + "' has been successfully created.");
    }
}