package org.tudai.reportservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.tudai.tripservice.entitity.Trip;

import java.util.Date;

@Getter
@Setter
@ToString
public class TripDTO {
    private Long id;
    private Long accountId;
    private Date startDateTime;
    private Date endDateTime;
    private Double distanceTraveled;
    private Double duration;
    private Double creditsConsumed;
    private long pauseTime;
    private Long scooterId;

    public TripDTO() {
    }

    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed, Long accountId, Long scooterId) {
        this.accountId = accountId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.scooterId = scooterId;
    }

    public TripDTO(Trip trip) {
        this.accountId = trip.getAccountId();
        this.startDateTime = trip.getStartDateTime();
        this.endDateTime = trip.getEndDateTime();
        this.distanceTraveled = trip.getDistanceTraveled();
        this.duration = trip.getDuration();
        this.creditsConsumed = trip.getCreditsConsumed();
    }

    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
    }

    public TripDTO(double v, double v1, double v2) {
        this.distanceTraveled=v;
        this.duration=v1;
        this.creditsConsumed=v2;
    }
}

