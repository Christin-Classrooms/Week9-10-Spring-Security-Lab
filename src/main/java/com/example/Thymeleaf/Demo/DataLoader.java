package com.example.Thymeleaf.Demo;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {

        if (playerRepository.findByUsername("admin").isEmpty()) {
            Player admin = new Player();
            admin.setUsername("admin");
            admin.setName("Admin User");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            playerRepository.save(admin);

            System.out.println("Admin user created.");
        }
    }
}
