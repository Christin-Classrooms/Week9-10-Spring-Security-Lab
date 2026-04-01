package com.example.Thymeleaf.Demo;

import com.example.Thymeleaf.Demo.Service.PlayerDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                // ✅ Public routes
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()

                // ✅ Admin-only routes
                .requestMatchers("/create-fighter").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").hasRole("ADMIN")

                // ✅ All others need login
                .anyRequest().authenticated()
            )

            // ✅ Connect your custom user service
            .authenticationProvider(authenticationProvider)

            // ✅ Custom login page
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            // ✅ Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        // ✅ Fix H2 console
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    // ✅ Authentication Provider (correct service)
    @Bean
    public AuthenticationProvider authenticationProvider(PlayerDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // ✅ Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}