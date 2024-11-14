package org.tudai.reportservice.dto;

public class ScooterReportTripYearDTO {
    private Integer scooterId;
    private Long tripCount;
    private Double kilometersTraveled;

    public ScooterReportTripYearDTO() {}

    public ScooterReportTripYearDTO(Integer scooterId, Long tripCount, Double kilometersTraveled) {
        this.scooterId = scooterId;
        this.tripCount = tripCount;
        this.kilometersTraveled = kilometersTraveled;
    }
}
