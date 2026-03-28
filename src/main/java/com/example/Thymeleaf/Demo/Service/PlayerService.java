package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    public Page<Player> getAllPlayersPageable(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Page<Player> findPlayerByName(String search, Pageable pageable) {
        return playerRepository.findByNameContainingIgnoreCase(search, pageable);
    }
}