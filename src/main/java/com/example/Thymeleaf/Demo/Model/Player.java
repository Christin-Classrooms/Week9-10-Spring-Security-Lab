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

    @NotBlank(message = "A name is required!")
    @Size(min = 2, max = 240, message = "name length must be > 2 and < 240 chars !!")
    private String name;

    @NotBlank(message = "An email is required!")
    @Email(message = "invalid email")
    private String email;

    // BCRYPT HASH GOES HERE NEVER PLAIN TEXT
    private String password;

    // ROLE = "PLAYER" OR "ADMIN"
    private String role;

}
