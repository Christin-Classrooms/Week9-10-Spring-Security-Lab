package com.example.Thymeleaf.Demo.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final PlayerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.findByUsername("admin").isEmpty()) {
            Player admin = new Player();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setEmail("admin@test.com");
            repo.save(admin);
        }
    }
}
