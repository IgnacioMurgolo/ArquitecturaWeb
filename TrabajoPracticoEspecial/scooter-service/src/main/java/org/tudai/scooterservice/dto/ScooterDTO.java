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


    public ScooterDTO() { }
}
