package com.example.Thymeleaf.Demo.controllers;

import com.example.Thymeleaf.Demo.Model.Fighter;
import com.example.Thymeleaf.Demo.repository.FighterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Fighter data.
 * Exposes endpoints to retrieve list of fighters or a single fighter by ID.
 * Excluded from authentication in SecurityConfig to allow service-to-service calls.
 */
@RestController
@RequestMapping("/api/fighters")
public class FighterRestController {

    private final FighterRepository fighterRepository;

    public FighterRestController(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    @GetMapping
    public List<Fighter> getAllFighters() {
        return fighterRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fighter> getFighterById(@PathVariable int id) {
        return fighterRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
