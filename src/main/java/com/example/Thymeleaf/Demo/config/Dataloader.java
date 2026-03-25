package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Dataloader {

    @Bean
    CommandLineRunner loadData(PlayerRepository repo, PasswordEncoder encoder) {
        return args -> {
            System.out.println("Dataloader is running...");

            if (repo.findByEmail("bimal@test.com") == null) {
                Player user = new Player();
                user.setName("Bimal");
                user.setEmail("bimal@test.com");
                user.setPassword(encoder.encode("1234"));
                user.setRole("PLAYER");
                repo.save(user);
                System.out.println("Inserted PLAYER user: bimal@test.com");
            }

            if (repo.findByEmail("admin@test.com") == null) {
                Player admin = new Player();
                admin.setName("Admin");
                admin.setEmail("admin@test.com");
                admin.setPassword(encoder.encode("admin"));
                admin.setRole("ADMIN");
                repo.save(admin);
                System.out.println("Inserted ADMIN user: admin@test.com");
            }
        };
    }
}