package org.tudai.userservice.dto;

import lombok.Getter;

import java.util.Date;

@Getter
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


    public TripDTO(Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed, AccountDTO account) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.account = account;
    }
}
