package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    Optional<Player> findByEmail(String email);
}
