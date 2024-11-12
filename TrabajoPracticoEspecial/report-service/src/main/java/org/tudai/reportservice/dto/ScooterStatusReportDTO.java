package org.tudai.reportservice.dto;

import lombok.Getter;

@Getter
public class ScooterStatusReportDTO {
    private long operationalScooters;  // Scooters disponibles
    private long maintenanceScooters;  // Scooters en mantenimiento

    public ScooterStatusReportDTO(long operationalScooters, long maintenanceScooters) {
        this.operationalScooters = operationalScooters;
        this.maintenanceScooters = maintenanceScooters;
    }

}
