package com.example.Thymeleaf.Demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Thymeleaf.Demo.Service.PlayerDetailsService;

// INIT SECURITY RULES
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PlayerDetailsService playerDetailsService;

    public SecurityConfig(PlayerDetailsService playerDetailsService) {
        this.playerDetailsService = playerDetailsService;
    }

    // ENCRYPTOR ALWAYS BCRYPT, NEVER PLAIN TEXT
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // THE FILTER CHAIN ORDRE MATTERS! SPECIFIC RULES FIRST
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                // PUBLIC NO LOGIN NEEDED
                .requestMatchers("/login", "/register").permitAll()
                // ADMIN ONLY ZONES
                .requestMatchers("/create-fighter").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").hasRole("ADMIN")
                // EVRYTHING ELSE MUST BE LOGGED IN
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                // CUSTOM LOGIN PAGE
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                )
                // H2 USES IFRAMES MUST DISABLE X-FRAME-OPTIONS
                .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
                )
                // DISABLE CSRF FOR H2 CONSOLE PATH ONLY
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                )
                .userDetailsService(playerDetailsService);

        return http.build();
    }
}
