package org.tudai.scooterservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScooterReportDTO {
    private Long id;
    private Boolean status;
    private String location;
    private Double kilometersTraveled;
    private Double hoursUsed;
    private Long currentStationId;
    private List<Long> scooterTripsIds;
    private List<Long> maintenanceIds;

    public ScooterReportDTO() {
    }

    public ScooterReportDTO(Long id, Boolean status, String location, Double kilometersTraveled, Double hoursUsed, Long currentStationId, List<Long> scooterTripsIds, List<Long> maintenanceIds) {
        this.id = id;
        this.status = status;
        this.location = location;
        this.kilometersTraveled = kilometersTraveled;
        this.hoursUsed = hoursUsed;
        this.currentStationId = currentStationId;
        this.scooterTripsIds = scooterTripsIds;
        this.maintenanceIds = maintenanceIds;
    }
}
