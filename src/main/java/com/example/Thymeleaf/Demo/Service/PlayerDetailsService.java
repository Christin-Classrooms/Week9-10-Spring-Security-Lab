package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Player player = playerRepository.findByUsername(username);

        if (player == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return user info to Spring Security
        return User.builder()
                .username(player.getUsername())   // login username
                .password(player.getPassword())   // login password
                .roles(player.getRole())         // role (PLAYER or ADMIN)
                .build();
    }
}