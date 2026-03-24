package com.example.Thymeleaf.Demo.Config;

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
    public void run(ApplicationArguments args) throws Exception {
        Player admin = playerRepository.findByEmail("admin@gmail.com");
        if (admin == null) {
            Player newAdmin = new Player();
            newAdmin.setName("admin");
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(passwordEncoder.encode("admin123")); // encrypt password
            newAdmin.setRole("ADMIN"); // assign role
            playerRepository.save(newAdmin);
            System.out.println("Admin user created: " + newAdmin.getEmail());
        } else {
            System.out.println("Admin user already exists: " + admin.getEmail());
        }
    }
}