package com.example.Thymeleaf.Demo.repository;

import com.example.Thymeleaf.Demo.Model.Fighter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Added for @Param
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Integer> {

    // Standard Derived Query: Finds by name ignoring case
    Page<Fighter> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Standard Derived Query: Finds by health floor
    Page<Fighter> findByHealthGreaterThan(int health, Pageable pageable);

    // Custom JPQL: Sorts by damage descending
    @Query("SELECT f FROM Fighter f ORDER BY f.damage DESC")
    Page<Fighter> findStrongestFighters(Pageable pageable);

    // Custom JPQL: Multi-conditional filter for "Balanced" fighters
    @Query("SELECT f FROM Fighter f WHERE f.health > :minHealth AND f.damage <= :maxDamage ORDER BY f.resistance DESC")
    Page<Fighter> findBalancedFighters(@Param("minHealth") double minHealth,
                                       @Param("maxDamage") double maxDamage,
                                       Pageable pageable);
}