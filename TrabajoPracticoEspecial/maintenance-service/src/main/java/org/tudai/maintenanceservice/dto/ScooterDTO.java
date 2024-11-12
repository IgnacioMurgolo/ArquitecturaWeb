package org.tudai.maintenanceservice.dto;

import lombok.Getter;

@Getter
public class ScooterDTO {
    private Long id;
    private Long kilometres;
    private String status;
    private Double kilometersTraveled;
    private Double hoursUsed;
    private Long currentStationId;
    private Long currentTripId;

    public ScooterDTO() {}

    public ScooterDTO(Long id, Long kilometres, String status, Double kilometersTraveled, Double hoursUsed, Long currentStationId, Long currentTripId) {
        this.id = id;
        this.kilometres = kilometres;
        this.status = status;
        this.kilometersTraveled = kilometersTraveled;
        this.hoursUsed = hoursUsed;
        this.currentStationId = currentStationId;
        this.currentTripId = currentTripId;
    }
}
