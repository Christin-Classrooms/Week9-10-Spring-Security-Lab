package com.example.Thymeleaf.Demo.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (playerRepository.findByUsername("admin").isEmpty()) {
            Player admin = new Player();
            admin.setName("Administrator");
            admin.setEmail("admin@tekken.com");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            playerRepository.save(admin);
        }
    }
}