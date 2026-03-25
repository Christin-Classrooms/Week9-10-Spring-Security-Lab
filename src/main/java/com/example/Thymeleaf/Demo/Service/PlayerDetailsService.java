package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // In this project, username = email
        Player player = playerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found with email: " + username));

        return new User(
                player.getEmail(),
                player.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + player.getRole()))
        );
    }
}