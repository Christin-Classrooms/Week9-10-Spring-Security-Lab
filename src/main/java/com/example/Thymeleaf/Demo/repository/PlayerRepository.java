package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    @Query("SELECT p FROM Player p WHERE p.email = :email")
    Player findByEmail(@Param("email") String email);

    Optional<Player> findByUsername(String username);
}