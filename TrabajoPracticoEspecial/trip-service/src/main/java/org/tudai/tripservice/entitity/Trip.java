package org.tudai.tripservice.entitity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId; // PARA LA RELACION CON Account
    private Date startDateTime;
    private Date endDateTime;
    private Double distanceTraveled;
    private Double duration;
    private Double creditsConsumed;
    @ElementCollection
    private List<Long> pausesId; // PARA LA RELACION CON Pause

    public Trip() {}

    public Trip(Long accountId, Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed) {
        this.accountId = accountId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.pausesId = new ArrayList<>();
    }
}
