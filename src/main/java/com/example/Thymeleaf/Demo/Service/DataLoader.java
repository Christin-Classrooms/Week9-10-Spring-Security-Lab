package com.example.Thymeleaf.Demo.Service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Component
public class DataLoader implements ApplicationRunner {
    
    private final PlayerRepository repo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        Player admin = repo.findByName("admin");

        if (admin == null) {
            Player newAdmin = new Player();
            newAdmin.setName("Admin");
            newAdmin.setEmail("admin@example.com");
            newAdmin.setPassword(passwordEncoder.encode("admin123"));
            newAdmin.setRole("ADMIN");

            repo.save(newAdmin);
        }
    }
}
