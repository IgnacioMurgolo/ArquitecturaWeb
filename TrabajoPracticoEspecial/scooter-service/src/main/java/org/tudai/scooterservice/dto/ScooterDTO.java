package org.tudai.scooterservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.tudai.scooterservice.entity.Scooter;

import java.util.List;

@Getter
@Setter
public class ScooterDTO {
    private Long id;
    private boolean status;
    private String ubication;
    private Double kilometersTraveled;
    private Double hoursUsed;
    private Long currentStationId;
    private Long currentTripId;
    private List<Long> maintenanceRecordIds;

    public ScooterDTO(Scooter newScooter) {
        this.id = newScooter.getId();
        this.status = newScooter.isStatus();
        this.ubication = newScooter.getUbication();
        this.kilometersTraveled = newScooter.getKilometersTraveled();
        this.hoursUsed = newScooter.getHoursUsed();
        this.currentStationId = newScooter.getCurrentStationId();
        this.currentTripId = newScooter.getCurrentTripId();
        this.maintenanceRecordIds = newScooter.getMaintenanceIds();
    }

    public ScooterDTO() { }
}
