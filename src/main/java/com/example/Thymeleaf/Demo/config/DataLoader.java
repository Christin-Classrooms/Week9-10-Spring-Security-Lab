package com.example.Thymeleaf.Demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Player existingAdmin = playerRepository.findByName("admin");

        if (existingAdmin == null) {
            Player admin = new Player();
            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            playerRepository.save(admin);
        }
    }
}