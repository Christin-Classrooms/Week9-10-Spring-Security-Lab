package com.example.Thymeleaf.Demo;


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
        Player admin = playerRepository.findByEmail("admin");

        if (admin == null) {
            Player adminUser = new Player();
          adminUser.setName("Admin");
          adminUser.setEmail("admin@example.com");
          adminUser.setPassword(passwordEncoder.encode("admin123"));
          adminUser.setRole("ADMIN");
          playerRepository.save(adminUser);
          System.out.println("Admin created");

        } else {
            System.out.println("Admin already exists");
        }
    }
}
