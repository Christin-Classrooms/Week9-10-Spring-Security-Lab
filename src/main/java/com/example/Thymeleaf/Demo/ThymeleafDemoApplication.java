package com.example.Thymeleaf.Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ThymeleafDemoApplication {

	public static void main(String[] args) {

		// ✅ TEMP: generate password hash
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("1234"); // 👈 your password
		System.out.println("GENERATED PASSWORD HASH: " + hash);

		SpringApplication.run(ThymeleafDemoApplication.class, args);
	}
}