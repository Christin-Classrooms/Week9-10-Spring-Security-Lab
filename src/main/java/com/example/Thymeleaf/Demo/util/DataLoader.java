package com.example.Thymeleaf.Demo.util;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final PlayerRepository playerRepo;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PlayerRepository playerRepo, PasswordEncoder passwordEncoder) {
        this.playerRepo = playerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Only add default admin if not exists
        if (playerRepo.findByUsername("administrator").isEmpty()) {
            Player admin = new Player();
            admin.setUsername("administrator");
            admin.setPassword(passwordEncoder.encode("Admin!234"));
            admin.setRole("ADMIN");
            playerRepo.save(admin);

            System.out.println("Default admin created: administrator / Admin!234");
        }
    }
}