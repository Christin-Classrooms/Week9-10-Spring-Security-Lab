package com.example.Thymeleaf.Demo.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = getUserFromDatabase(username);
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
    
    private User getUserFromDatabase(String username) {
        return null;
    }
}
