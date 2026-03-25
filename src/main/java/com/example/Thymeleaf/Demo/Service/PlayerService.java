package com.example.Thymeleaf.Demo.service;

import com.example.Thymeleaf.Demo.model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private PlayerRepository repo;

    public PlayerService(PlayerRepository repo) {

        this.repo = repo;
    }

    public List<Player> getAllPlayers() {
        
        return repo.findAll();
    }

    public Page<Player> getAllPlayersPageable(Pageable pageable) {

        return repo.findAll(pageable);

    }

    public void addPlayer(Player player) {

        repo.save(player);
    }

    public Page<Player> findPlayerByName(String name, Pageable page){

        return repo.findByNameContainingIgnoreCase(name,page);
    }

    public Player getPlayerById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Player getPlayerByEmail(String email) {
    return repo.findByEmail(email);
}



}
