package org.tudai.tripservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TripDTO {
    private String id;
    private String accountId;
    private Date startDateTime;
    private Date endDateTime;
    private Double distanceTraveled;
    private Double duration;
    private Double creditsConsumed;
    private Long pauseTime;
    private Long scooterId;

    public TripDTO() {
    }

    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed, String accountId, Long scooterId) {
        this.accountId = accountId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.scooterId = scooterId;
    }

    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
    }
}
