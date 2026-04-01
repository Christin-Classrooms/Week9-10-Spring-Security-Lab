package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository repo;

    public PlayerService(PlayerRepository repo) {
        this.repo = repo;
    }

    // Get all players
    public List<Player> getAllPlayers() {
        return repo.findAll();
    }

    // Get players with pagination
    public Page<Player> getAllPlayersPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Add or update player
    public void addPlayer(Player player) {
        repo.save(player);
    }

    // Search by name
    public Page<Player> findPlayerByName(String name, Pageable page) {
        return repo.findByNameContainingIgnoreCase(name, page);
    }

    // Get by ID
    public Player getPlayerById(int id) {
        return repo.findById(id).orElse(null);
    }

    // ✅ Get by username (Optional handling)
    public Player getPlayerByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    // ✅ Get by email (Optional handling)
    public Player getPlayerByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }
}