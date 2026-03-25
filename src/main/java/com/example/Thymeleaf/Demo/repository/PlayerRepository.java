package com.example.Thymeleaf.Demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Thymeleaf.Demo.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    @Query("Select p from Player p where p.email =:email")
    Player findByEmail(@Param("email") String email);

    Player findByName(String name);
}