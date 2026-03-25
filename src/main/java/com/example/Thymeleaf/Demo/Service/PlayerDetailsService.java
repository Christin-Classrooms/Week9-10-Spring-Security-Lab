package com.example.Thymeleaf.Demo.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // looking up the player by email (we're using email as the username)
        Player player = playerRepository.findByEmail(username);
        

        // added exception for testing
        if (player == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        
        // building the spring security userdetails object
        return User.builder()
                .username(player.getEmail())
                .password(player.getPassword())
                .roles(player.getRole())
                // self-note: spring is gonna automatically adds the "ROLE_" prefix
                .build();
    }
}