package com.example.Thymeleaf.Demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class TestConfig {

    @Bean
    public CommandLineRunner printEncodedPassword(BCryptPasswordEncoder encoder) {
        return args -> {
            String rawPassword = "1234";
            String encodedPassword = encoder.encode(rawPassword);

            System.out.println("Encoded password for '1234': " + encodedPassword);
        };
    }
}