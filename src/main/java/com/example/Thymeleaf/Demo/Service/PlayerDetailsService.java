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
        Player player = playerRepository.findByEmail(username);
        if (player != null) {
            return User.withUsername(player.getEmail())
                    .password(player.getPassword())
                    .roles(player.getRole())
                    .build();
        }
        
        throw new UsernameNotFoundException("Please add a username field to your Player entity and a findByUsername method to PlayerRepository");
    }
}