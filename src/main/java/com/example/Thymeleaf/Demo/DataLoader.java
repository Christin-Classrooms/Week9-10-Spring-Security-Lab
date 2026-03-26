package com.example.Thymeleaf.Demo;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Part 7 - DataLoader: seeds an ADMIN user on every startup.
 *
 * The guard check (findByName("admin") == null) ensures we only insert
 * the admin once. Without it, H2's create-drop would recreate the table
 * on restart anyway — but the pattern is good practice for real databases.
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        // Only seed if admin does not already exist
        if (playerRepository.findByName("admin") == null) {
            Player admin = new Player();
            admin.setName("admin");
            admin.setEmail("admin@tekken.com");
            // Always BCrypt-encode — NEVER store plain text
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            playerRepository.save(admin);
            System.out.println(">>> Admin user seeded successfully.");
        } else {
            System.out.println(">>> Admin user already exists — skipping seed.");
        }
    }
}
