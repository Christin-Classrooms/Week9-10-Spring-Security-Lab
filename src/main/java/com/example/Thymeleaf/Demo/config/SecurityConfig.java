package com.example.Thymeleaf.Demo.config;

import com.example.Thymeleaf.Demo.Service.PlayerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * Part 4 - Security configuration.
 *
 * Defines:
 *   1. A BCryptPasswordEncoder bean (used everywhere passwords are encoded/verified)
 *   2. The SecurityFilterChain with all route rules, form login, logout, and
 *      H2 console special handling (CSRF off + X-Frame-Options disabled for /h2-console/**)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ─────────────────────────────────────────────────────
    // Part 4 – Bean 1: Password encoder (BCrypt)
    // ─────────────────────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ─────────────────────────────────────────────────────
    // Part 4 – Bean 2: SecurityFilterChain (all the rules)
    // ─────────────────────────────────────────────────────
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ── Route authorisation rules (ORDER MATTERS – most specific first) ──
                .authorizeHttpRequests(auth -> auth

                        // /login and /register are public – no login needed
                        .requestMatchers("/login", "/register").permitAll()

                        // Only ADMIN can create a fighter or access the H2 console
                        .requestMatchers("/create-fighter").hasRole("ADMIN")
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")

                        // Everything else requires any authenticated user
                        .anyRequest().authenticated()
                )

                // ── Form login ──
                .formLogin(form -> form
                        .loginPage("/login")           // our custom login page (GET /login)
                        .defaultSuccessUrl("/", true)  // redirect to / after successful login
                        .permitAll()
                )

                // ── Logout ──
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // ── H2 console special config ──
                // The H2 web console uses <iframe> tags internally.
                // Spring Security's X-Frame-Options header blocks iframes by default,
                // and CSRF protection breaks the console's form submissions.
                // We disable both — but ONLY for the h2-console path.
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                );

        return http.build();
    }
}
