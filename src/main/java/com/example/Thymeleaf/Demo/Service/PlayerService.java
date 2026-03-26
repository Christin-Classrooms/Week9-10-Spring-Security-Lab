package com.example.Thymeleaf.Demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Service
public class PlayerService implements UserDetailsService {

    private final PlayerRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PlayerService(PlayerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = repo.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(player.getName())
                .password(player.getPassword())
                .authorities(player.getRole())
                .build();
    }

    public void addPlayer(Player player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));

        if (player.getRole() == null || player.getRole().isEmpty()) {
            player.setRole("ROLE_PLAYER");
        } else if (!player.getRole().startsWith("ROLE_")) {
            player.setRole("ROLE_" + player.getRole());
        }

        repo.save(player);
    }

    public List<Player> getAllPlayers() {
        return repo.findAll();
    }

    public Page<Player> getAllPlayersPageable(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<Player> findPlayerByName(String name, Pageable page) {
        return repo.findByNameContainingIgnoreCase(name, page);
    }

    public Player getPlayerById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}