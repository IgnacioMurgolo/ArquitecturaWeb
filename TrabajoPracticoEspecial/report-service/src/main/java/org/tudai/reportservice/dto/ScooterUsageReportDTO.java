package org.tudai.reportservice.dto;

import lombok.Getter;

@Getter
public class ScooterUsageReportDTO {
    private Long scooterId;
    private double totalDistance;       // Total de kilómetros recorridos
    private double totalDuration;       // Duración total del uso, incluyendo pausas si aplica

    public ScooterUsageReportDTO(Long scooterId, double totalDistance, double totalDuration) {
        this.scooterId = scooterId;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
    }
}
