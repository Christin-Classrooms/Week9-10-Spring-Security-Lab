package com.example.Thymeleaf.Demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max=240, message="Name size must be > 2 and <240")
    @NotBlank(message="The name is required")
    private String name;

    @NotBlank(message = "The email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "The username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "The password is required")
    private String password;

    @NotBlank(message = "The role is required")
    private String role;

    // =======================
    // Getter and Setter methods
    // =======================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}