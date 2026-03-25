package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // check whether the admin user already exists
        Player existingAdmin = playerRepository.findByEmail("admin@example.com");
        
        if (existingAdmin == null) {
            // creating the admin user
            Player admin = new Player();
            admin.setName("Administrator");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            
            playerRepository.save(admin);
            System.out.println("Admin user created!");
            System.out.println("Email: SteveAdmin@Mail.com");
            System.out.println("Password: n01363509");
        } else {
            System.out.println("Admin user already exists");
        }
    }
}