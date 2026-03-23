package com.example.Thymeleaf.Demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Username is required.")
    @Size(min=3, max=50, message="Username must be between 3 and not too long.")
    private String username;
    
    @Size(min=2, max=240, message="name size must be realistic")
    @NotBlank(message="The name is required")
    private String name;
    
    @NotBlank(message = "The email is required")
    @Email(message = "a very invalid email")
    private String email;
    
    private String password;
    
    private String role;
}