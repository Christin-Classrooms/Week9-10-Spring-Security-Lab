package com.example.Thymeleaf.Demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplified demonstrations
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/login", "/register").permitAll() // Open to all
                                                .requestMatchers("/h2-console/**").hasRole("ADMIN") // Restricted to
                                                                                                    // ADMIN
                                                .requestMatchers("/admin/**").hasRole("ADMIN") // Restricted to ADMIN
                                                .anyRequest().authenticated() // Catch-all: Must be logged in
                                );
                // ... (login/logout config)
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
