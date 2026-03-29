package com.example.Thymeleaf.Demo.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Service
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository repo;

    public PlayerDetailsService(PlayerRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Player player = repo.findByEmail(email);

        if (player == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(player.getEmail())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}