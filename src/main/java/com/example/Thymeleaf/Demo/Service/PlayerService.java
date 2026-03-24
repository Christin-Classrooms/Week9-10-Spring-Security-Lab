package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Player> getAllPlayers() {
        return repo.findAll();
    }

    public Page<Player> getAllPlayersPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public void addPlayer(Player player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        player.setRole("PLAYER");
        repo.save(player);
    }

    public Page<Player> findPlayerByName(String name, Pageable page){
        return repo.findByNameContainingIgnoreCase(name,page);
    }

    public Player getPlayerById(int id) {
        return repo.findById(id).orElse(null);
    }

}
