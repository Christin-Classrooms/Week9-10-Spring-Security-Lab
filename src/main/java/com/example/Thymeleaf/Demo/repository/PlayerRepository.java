package com.example.Thymeleaf.Demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Thymeleaf.Demo.Model.Player;

// NOT USED ANYMORE
// import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    // EXACT MATCH USED FOR LOGIN LOOKUP
    Player findByName(String name);

    Page<Player> findByNameContainingIgnoreCase(String name, Pageable page);

    // AW YEAH. JPQL
    @Query("Select p from Player p where p.email =:email")
    Player findByEmail(@Param("email") String email);

}
