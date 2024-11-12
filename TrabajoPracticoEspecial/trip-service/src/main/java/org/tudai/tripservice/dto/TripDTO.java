package org.tudai.tripservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.tudai.tripservice.entitity.Trip;
import org.tudai.userservice.dto.AccountDTO;

import java.util.Date;

@Getter
@Setter
@ToString
public class TripDTO {
    private Long id;
    private Long accountId; // Solo el ID
    private AccountDTO account; // El DTO completo para mostrar
    private Date startDateTime;
    private Date endDateTime;
    private Double distanceTraveled;
    private Double duration;
    private Double creditsConsumed;
    private long pauseTime;

    public TripDTO() {
    }

    public TripDTO(Long accountId,Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed) {
        this.accountId = accountId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
    }

    public TripDTO(Trip trip) {
        this.accountId = trip.getAccountId();
        this.startDateTime = trip.getStartDateTime();
        this.endDateTime = trip.getEndDateTime();
        this.distanceTraveled = trip.getDistanceTraveled();
        this.duration = trip.getDuration();
        this.creditsConsumed = trip.getCreditsConsumed();
    }

    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed, AccountDTO account) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.account = account;
    }
}
