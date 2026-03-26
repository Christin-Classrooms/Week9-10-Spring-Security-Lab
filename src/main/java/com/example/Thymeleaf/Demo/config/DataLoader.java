package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection is preferred over @Autowired on fields
    public DataLoader(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1. Check if the admin already exists (using email as the unique identifier)
        Optional<Player> adminCheck = playerRepository.findByEmail("admin@tekkenreborn.com");

        if (adminCheck.isEmpty()) {
            // 2. Create the Admin Player
            Player admin = new Player();
            admin.setName("System Admin");
            admin.setEmail("admin@tekkenreborn.com");

            // 3. Encode the password!
            admin.setPassword(passwordEncoder.encode("admin123"));

            // 4. Assign the ADMIN role
            admin.setRole("ADMIN");

            // 5. Save to database
            playerRepository.save(admin);

            System.out.println("--- DataLoader: Admin user created (admin@tekkenreborn.com / admin123) ---");
        } else {
            System.out.println("--- DataLoader: Admin user already exists. Skipping seed. ---");
        }
    }
}