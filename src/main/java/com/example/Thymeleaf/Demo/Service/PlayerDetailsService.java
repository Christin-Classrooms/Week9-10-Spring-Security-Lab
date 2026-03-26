package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    public PlayerDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Log this to see if the email is actually reaching the service
        System.out.println("Login attempt for: " + email);

        Player player = playerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return User.builder()
                .username(player.getEmail())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}