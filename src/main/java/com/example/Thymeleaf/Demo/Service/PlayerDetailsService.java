package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PlayerDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerDetailsService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

        Player player = playerRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("Player Not Found: " + username));

        return User.builder()
                .username(player.getUsername())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }

        public void addPlayer(Player player) {
            // Encode password before saving
            String encodedPassword = passwordEncoder.encode(player.getPassword());
            player.setPassword(encodedPassword);
            playerRepository.save(player);
        }
    }
