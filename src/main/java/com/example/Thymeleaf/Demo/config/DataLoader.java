package com.example.Thymeleaf.Demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;

@Configuration
public class DataLoader {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public ApplicationRunner loadAdminUser() {
        return args -> {
            Player existingAdmin = playerService.findByUsername("admin");
            
            if (existingAdmin == null) {
                Player admin = new Player();
                admin.setUsername("admin");
                admin.setName("Administrator");
                admin.setEmail("admin@email.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                
                playerService.savePlayer(admin);
                System.out.println("ADMIN USER CREATED!");
                System.out.println("Username: admin");
                System.out.println("Password: admin123");
                System.out.println("Role: ADMIN");
            } else {
                System.out.println("Admin user already exists!");
            }
        };
    }
}