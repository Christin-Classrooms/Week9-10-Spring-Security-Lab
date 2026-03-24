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
    private PlayerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Player player = repo.findByName(name);

        if (player == null) {
            throw new UsernameNotFoundException("Player not found: " + name);
        }

        return User.builder()
                .username(player.getName())
                .password(player.getPassword())
                .roles(player.getRole())
                .build();
    }
}
