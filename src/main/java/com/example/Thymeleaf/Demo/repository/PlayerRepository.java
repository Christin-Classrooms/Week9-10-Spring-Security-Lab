package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    //JPQL
    @Query("Select p from Player p where p.email =:email")
    Player findByEmail(@Param("email") String email);

    @Query("Select p from Player p where p.username = :username")
    Player findByUsername(@Param("username") String username);

}
