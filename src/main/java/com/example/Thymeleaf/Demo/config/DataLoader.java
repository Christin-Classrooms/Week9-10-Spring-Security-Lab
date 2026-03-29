package com.example.Thymeleaf.Demo.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.repository.PlayerRepository;

@Configuration
public class DataLoader {

    @Bean
    public ApplicationRunner init(PlayerRepository repo, BCryptPasswordEncoder encoder) {
        return args -> {

            if (repo.findByEmail("admin@gmail.com") == null) {

                Player admin = new Player();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");

                repo.save(admin);
            }
        };
    }
}