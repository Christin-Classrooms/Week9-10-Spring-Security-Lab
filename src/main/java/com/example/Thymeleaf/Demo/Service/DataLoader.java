package com.example.Thymeleaf.Demo.Service;

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
        Player admin = playerRepository.findByName("admin");

        if (admin == null) {
            Player newAdmin = new Player();
            newAdmin.setName("admin");
            newAdmin.setEmail("admin@example.com");
            newAdmin.setPassword(passwordEncoder.encode("admin123"));
            newAdmin.setRole("ADMIN");

            playerRepository.save(newAdmin);
        }
    }
}
