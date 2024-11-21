package org.tudai.scooterservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.tudai.scooterservice.entity.Station;

import java.util.List;

@Getter
@Setter
public class StationDTO {
    private String name;
    private String ubication;
    private List<Long> scootersId;

    public StationDTO(String name, String ubication, List<Long> scootersId) {
        this.name = name;
        this.ubication = ubication;
        this.scootersId = scootersId;
    }

    public StationDTO(Station station) {
        this.name = station.getName();
        this.ubication = station.getUbication();
        this.scootersId = station.getScootersId();
    }

    public StationDTO() {

    }
}
