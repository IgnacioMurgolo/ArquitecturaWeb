package org.tudai.adminservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    private Double futurePrice;         // Precio futuro que será efectivo en la fecha indicada
    private LocalDate effectiveDate;    // Fecha de efectividad para el nuevo precio

    public Admin(String name, String email, Double pricePerKm, Double extraPricePause) {
        this.name = name;
        this.email = email;
        this.pricePerKm = pricePerKm;
        this.extraPricePause = extraPricePause;
    }

    public Admin(String name, String email, Double pricePerKm, Double extraPricePause, LocalDate effectiveDate, Double futurePrice) {
        this.name = name;
        this.email = email;
        this.pricePerKm = pricePerKm;
        this.extraPricePause = extraPricePause;
        this.effectiveDate = effectiveDate;
        this.futurePrice = futurePrice;
    }

    public Admin() {}
}
