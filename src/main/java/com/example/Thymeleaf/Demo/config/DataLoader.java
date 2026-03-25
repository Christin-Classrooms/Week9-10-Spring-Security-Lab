package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private final PlayerRepository playerRepo;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository playerRepo, PasswordEncoder passwordEncoder) {
        this.playerRepo = playerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (playerRepo.findByName("admin").isEmpty()) {
            Player admin = new Player();
            admin.setName("admin");
            admin.setEmail("admin123@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            playerRepo.save(admin);
        }
    }
}
