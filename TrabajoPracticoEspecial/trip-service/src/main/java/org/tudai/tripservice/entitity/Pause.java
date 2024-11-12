package org.tudai.tripservice.entitity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Pause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startPause;
    private Date endPause;
    private Boolean exceededTime;
    private Long tripId;

    public Pause(Date startPause, Date endPause, Boolean exceededTime, Long tripId) {
        this.startPause = startPause;
        this.endPause = endPause;
        this.exceededTime = exceededTime;
        this.tripId = tripId;
    }

    public Pause() {}
}
