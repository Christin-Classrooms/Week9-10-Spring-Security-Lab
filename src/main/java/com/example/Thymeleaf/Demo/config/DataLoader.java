package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepo;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private PlayerRepo playerRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner loadAdminUser() {
        return args -> {

            
            boolean adminExists = false;
            try {
                List<Player> allPlayers = playerRepo.findAll();
                for (Player player : allPlayers) {
                    if ("admin".equals(player.getName())) {
                        adminExists = true;
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Database not ready yet");
            }
            
            if (!adminExists) {
                Player admin = new Player();
                admin.setName("admin"); 
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                playerRepo.save(admin);
                System.out.println("Admin user created with email: " + admin.getEmail());
                
                System.out.println("=== WARNING ===");
                System.out.println("Your PlayerRepo.save() only saves name and email.");
                System.out.println("You need to add password and role fields to:");
                System.out.println("1. Player entity class");
                System.out.println("2. PlayerRepo.save() method");
                System.out.println("3. Player table in database");
            }
        };
    }
}