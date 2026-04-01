package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    // For pagination/search
    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    // For Spring Security login
    Optional<Player> findByUsername(String username);

    Optional<Player> findByEmail(String email);
}