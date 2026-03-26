package com.example.Thymeleaf.Demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

// BRIGDE BETWEEN OUR PLAYER TABLE AND SPRING SECURITY
@Service
public class PlayerDetailsService implements UserDetailsService {

    // REPO FOR FETCHING PLAYERS
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // LOOK UP PLAYER BY NAME
        // NOTE : NAME = USERNAME
        Player player = playerRepository.findByName(username);

        if (player == null) {
            // NOT FOUND = DOOM!
            throw new UsernameNotFoundException("No player found: " + username + "!!");
        }

        // BUILD SPRING SECURITY USER ROLES()
        return User.builder()
                .username(player.getName())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}
