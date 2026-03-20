package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    Player findByEmail(String email);
}