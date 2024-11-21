package org.tudai.reportservice.dto;

import lombok.Getter;
import lombok.Setter;

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

    public ScooterDTO(Long id) {}

    public ScooterDTO(long l, double v) {
        this.id = l;
        this.kilometersTraveled = v;
    }
}
