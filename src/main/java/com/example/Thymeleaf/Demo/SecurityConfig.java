package com.example.Thymeleaf.Demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Thymeleaf.Demo.Service.PlayerService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PlayerService playerService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(playerService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/h2-console/**", "/css/**", "/js/**").permitAll()
                        
                        .requestMatchers("/create-fighter/**").hasRole("ADMIN")
                        
                        .anyRequest().authenticated())
                
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}