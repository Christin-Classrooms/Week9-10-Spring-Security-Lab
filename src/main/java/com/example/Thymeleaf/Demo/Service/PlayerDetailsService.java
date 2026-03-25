package com.example.Thymeleaf.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Service
public class PlayerDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Look up player by email (using email as the username)
        Player player = playerRepository.findByEmail(username);
        
        if (player == null) {
            throw new UsernameNotFoundException("Player not found with email: " + username);
        }
        
        // Build and return Spring Security UserDetails object
        return User.builder()
                .username(player.getEmail())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}
