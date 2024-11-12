package org.tudai.maintenanceservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date maintenanceDate;
    private String maintenanceDescription;
    private String maintenanceStatus;
    private Long scooterId;

    public Maintenance() {}

    public Maintenance(Long id, Date maintenanceDate, String maintenanceDescription, String maintenanceStatus, Long scooterId) {
        this.id = id;
        this.maintenanceDate = maintenanceDate;
        this.maintenanceDescription = maintenanceDescription;
        this.maintenanceStatus = maintenanceStatus;
        this.scooterId = scooterId;
    }
}
