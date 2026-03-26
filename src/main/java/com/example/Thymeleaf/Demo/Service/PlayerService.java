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

    // Constructor Injection (Best Practice)
    public PlayerService(PlayerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public void addPlayer(Player player) {
        // 1. Hash the password before it touches the database
        String encodedPassword = passwordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);

        // 2. Set default role if empty (Spring Security roles usually expect 'USER' or 'ADMIN')
        if (player.getRole() == null || player.getRole().isEmpty()) {
            player.setRole("USER");
        }

        // 3. Save to H2
        repo.save(player);
        System.out.println("DEBUG: Successfully saved player: " + player.getEmail());
    }

    public List<Player> getAllPlayers() {
        return repo.findAll();
    }

    public Page<Player> getAllPlayersPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Player> findPlayerByName(String name, Pageable page){
        return repo.findByNameContainingIgnoreCase(name, page);
    }

    public Player getPlayerById(int id) {
        return repo.findById(id).orElse(null);
    }
}