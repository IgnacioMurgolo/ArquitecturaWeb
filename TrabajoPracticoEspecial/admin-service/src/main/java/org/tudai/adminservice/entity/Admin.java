package org.tudai.adminservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Double pricePerKm;
    private Double extraPricePause;

    public Admin(String name, String email, Double pricePerKm, Double extraPricePause) {
        this.name = name;
        this.email = email;
        this.pricePerKm = pricePerKm;
        this.extraPricePause = extraPricePause;
    }

    public Admin() {}
}
