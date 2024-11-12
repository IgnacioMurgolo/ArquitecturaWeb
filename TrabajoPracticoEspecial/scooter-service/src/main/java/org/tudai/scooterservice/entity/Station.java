package org.tudai.scooterservice.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Station {
    @Id
    private Long id;
    private String name;
    private String ubication;
    @ElementCollection
    private List<Long> scootersId;

    public Station(String name, String ubication, List<Long> scootersId) {
        this.name = name;
        this.ubication = ubication;
        this.scootersId = scootersId;
    }

    public Station() {}
}
