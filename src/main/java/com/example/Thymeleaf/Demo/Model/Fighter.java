package com.example.Thymeleaf.Demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "fighters")
public class Fighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotNull
    private Integer health;

    @NotNull
    private Double damage;

    @NotNull
    private Double resistance;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getHealth() { return health; }
    public void setHealth(Integer health) { this.health = health; }

    public Double getDamage() { return damage; }
    public void setDamage(Double damage) { this.damage = damage; }

    public Double getResistance() { return resistance; }
    public void setResistance(Double resistance) { this.resistance = resistance; }
}