package org.tudai.scooterservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private String ubication;
    private Double kilometersTraveled;
    private Double hoursUsed;
    private Long currentStationId;        // ID de la estación actual donde está estacionado
    private Long currentTripId;           // ID del viaje actual, si el scooter está en uso
    @ElementCollection
    private List<Long> maintenanceIds = new ArrayList<>();  // IDs de registros de mantenimiento

    public Scooter() {
        this.status = true;
    }



}

