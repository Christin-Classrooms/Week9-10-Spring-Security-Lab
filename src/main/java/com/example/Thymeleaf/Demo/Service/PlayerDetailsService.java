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

    private PlayerRepository playerRepository;

    public PlayerDetailsService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Player player = playerRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail);

        if (player == null) {
            throw new UsernameNotFoundException("Username not found: " + usernameOrEmail);
        }

        return User.withUsername(player.getEmail())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}
