package com.example.Thymeleaf.Demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

// SEED RUNS ON STARTUP TO ENSURE ADMIN EXISTS FROM VERY FIRST RUN
@Component
public class DataLoader implements ApplicationRunner {

    // NOTE - NEAT STYLE OF DEPENDENCY INJECTION!
    
    // CHECK IF ADMIN EXISTS
    @Autowired
    private PlayerRepository playerRepository;

    // HASH THE ADMIN PASSWORD
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // CHECK FIRST AVOID DUPLICATE ON RESTART
        if (playerRepository.findByName("admin") == null) {

            Player admin = new Player();
            admin.setName("admin");
            admin.setEmail("admin@admin.com");

            // BCRYPT THE PASSWORD NEVER PLAIN TEXT
            admin.setPassword(passwordEncoder.encode("admin123"));

            // FULL ACCES ADMIN ROLE
            admin.setRole("ADMIN");

            playerRepository.save(admin);
        }
    }
}
