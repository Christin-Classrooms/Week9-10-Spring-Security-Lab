package com.example.Thymeleaf.Demo.Service;


import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Part 3 - Bridges the Player entity with Spring Security.
 *
 * Spring Security calls loadUserByUsername() during login.
 * We look up the Player by their name (which acts as the username),
 * then wrap it in a Spring Security UserDetails object.
 */
@Service
public class PlayerDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Look up the player by name (our "username" field)
        Player player = playerRepository.findByName(username);

        if (player == null) {
            throw new UsernameNotFoundException("No player found with username: " + username);
        }

        // Build and return a Spring Security UserDetails object.
        // .roles() automatically prepends "ROLE_" — so store "PLAYER" or "ADMIN",
        // NOT "ROLE_PLAYER" / "ROLE_ADMIN", to avoid double-prefixing.
        return User.builder()
                .username(player.getName())
                .password(player.getPassword())
                .roles(player.getRole())   // e.g. "PLAYER" → authority "ROLE_PLAYER"
                .build();
    }
}
