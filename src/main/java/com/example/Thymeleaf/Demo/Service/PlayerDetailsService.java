package com.example.Thymeleaf.Demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;


@Service
public class PlayerDetailsService implements UserDetailsService {
    @Autowired
    private PlayerRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Player user = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
    }
}