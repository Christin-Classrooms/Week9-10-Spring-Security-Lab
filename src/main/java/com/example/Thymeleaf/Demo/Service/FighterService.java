package com.example.Thymeleaf.Demo.service;

import com.example.Thymeleaf.Demo.model.Fighter;
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

    public Page<Fighter> getFighters(Pageable pageable, String search, String filterType) {
        if (filterType == null) filterType = "all";

        switch (filterType.toLowerCase()) {
            case "name":
                return fighterRepository.findByNameContainingIgnoreCase(search != null ? search : "", pageable);
            case "health":
                int health = 0;
                try {
                    health = search != null ? Integer.parseInt(search) : 0;
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

    public void addFighter(Fighter fighter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFighter'");
    }
}
