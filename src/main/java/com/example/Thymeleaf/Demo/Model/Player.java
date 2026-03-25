package com.example.Thymeleaf.Demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max=240, message="name size must be > 2 and <240")
    @NotBlank(message="The name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String password;

    @NotBlank(message = "The email is required")
    @Email(message = "invalid email")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String role = "PLAYER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() { return this.name; }
}
