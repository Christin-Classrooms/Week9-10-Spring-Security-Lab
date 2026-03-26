package com.example.Thymeleaf.Demo.controllers;


import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Part 6 - Registration controller.
 *
 * Replaces the old /create-player endpoints.
 * Key differences from the original:
 *   - Routes changed to GET /register and POST /register
 *   - Password is BCrypt-encoded before saving (never plain text!)
 *   - Role is always set to "PLAYER" for self-registered users
 *   - Redirects to /login after successful registration
 */
@Controller
public class RegisterController {

    private final PlayerService playerService;
    private final PasswordEncoder passwordEncoder;   // injected from SecurityConfig bean

    public RegisterController(PlayerService playerService, PasswordEncoder passwordEncoder) {
        this.playerService = playerService;
        this.passwordEncoder = passwordEncoder;
    }

    // Part 6: Show the registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";   // → templates/register.html
    }

    // Part 6: Handle registration form submission
    @PostMapping("/register")
    public String registerPlayer(@Valid Player player, BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        // Encode the password with BCrypt before persisting — NEVER store plain text
        player.setPassword(passwordEncoder.encode(player.getPassword()));

        // Every self-registered user gets the PLAYER role
        player.setRole("PLAYER");

        playerService.addPlayer(player);

        // Redirect to login so the new user can log in immediately
        return "redirect:/login";
    }
}
