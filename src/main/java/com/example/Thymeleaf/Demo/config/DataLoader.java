package com.example.Thymeleaf.Demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if admin user already exists
        Player existingAdmin = playerRepository.findByEmail("admin@admin.com");

        if (existingAdmin == null) {
            // Create new admin user
            Player admin = new Player();
            admin.setName("Admin User");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            // Save to database
            playerRepository.save(admin);
            System.out.println("Admin user created successfully!");
            System.out.println("Admin credentials - Email: admin@admin.com, Password: admin123");
        } else {
            System.out.println("Admin user already exists, skipping creation.");
        }
    }
}
