package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p FROM Player p WHERE p.email = :email")
    Optional<Player> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Player p WHERE p.username = :username")
    Optional<Player> findByUsername(@Param("username") String username);

    // ADD THIS METHOD - it's used by PlayerService.findPlayerByName
    @Query("SELECT p FROM Player p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Player> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}