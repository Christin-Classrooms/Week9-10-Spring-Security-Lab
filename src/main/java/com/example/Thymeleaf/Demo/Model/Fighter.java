package com.example.Thymeleaf.Demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fighters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Fighter name is required")
    @Size(min=2, max=240, message="Name must be between 2 and 240 characters")
    private String name;
    
    @NotNull(message = "Health is required")
    @Min(value=1001, message="Health must be more than 1000")
    @Max(value=1499, message="Health must be less than 1500")
    private Integer health;
    
    @NotNull(message = "Damage is required")
    @DecimalMin(value="0.0", inclusive=true, message="Damage must be at least 0")
    @DecimalMax(value="99.99", inclusive=true, message="Damage must be less than 100")
    private Double damage;
    
    @NotNull(message = "Resistance is required")
    @DecimalMin(value="0.0", inclusive=true, message="Resistance must be at least 0")
    @DecimalMax(value="10.0", inclusive=true, message="Resistance must be at most 10")
    private Double resistance;

}
