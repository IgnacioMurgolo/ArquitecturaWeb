package org.tudai.reportservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudai.reportservice.dto.ScooterStatusReportDTO;
import org.tudai.reportservice.dto.ScooterUsageReportDTO;
import org.tudai.reportservice.feign.ScooterClient;
import org.tudai.reportservice.feign.TripClient;
import org.tudai.reportservice.feign.UserClient;
import org.tudai.scooterservice.dto.ScooterDTO;
import org.tudai.scooterservice.entity.Scooter;
import org.tudai.tripservice.dto.TripDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    private final TripClient tripClient;
    private final ScooterClient scooterClient;
    private final UserClient userClient;

    @Autowired
    public ReportService(TripClient tripClient, ScooterClient scooterClient, UserClient userClient) {
        this.tripClient = tripClient;
        this.scooterClient = scooterClient;
        this.userClient = userClient;
    }

    public List<ScooterUsageReportDTO> getScooterUsageReport(boolean includePauseTime) {
        List<ScooterUsageReportDTO> report = new ArrayList<>();

        // Obtener todos los scooters a través del cliente Feign
        List<ScooterDTO> scooters = scooterClient.getAll();
        for (ScooterDTO scooter : scooters) {
            // Obtener los viajes del scooter actual usando TripClient
            List<TripDTO> trips = tripClient.getTripsByScooterId(scooter.getId());
            ScooterUsageReportDTO scooterReport = getScooterUsageReportDTO(includePauseTime, scooter, trips);
            report.add(scooterReport);
        }
        return report;
    }

    private static ScooterUsageReportDTO getScooterUsageReportDTO(boolean includePauseTime, ScooterDTO scooter, List<TripDTO> trips) {
        double totalDistance = 0.0;
        double totalDuration = 0.0;

        // Calcular la distancia y duración totales
        for (TripDTO trip : trips) {
            totalDistance += trip.getDistanceTraveled();
            totalDuration += trip.getDuration();

            // Incluir el tiempo de pausa si se solicita
            if (includePauseTime) {
                totalDuration += trip.getPauseTime();
            }
        }
        // Crear el reporte de uso para este scooter
        ScooterUsageReportDTO scooterReport = new ScooterUsageReportDTO(
                scooter.getId(), totalDistance, totalDuration);
        return scooterReport;
    }

    public void disableAccount(Long accountId) {
        // Encuentra la cuenta por ID y marca su estado como inhabilitado
        userClient.updateAccountStatus(accountId, false);
    }

    public List<ScooterDTO> getScootersWithMoreThanXTrips(int year, int minTrips) {
        // Llama a TripService para obtener la cantidad de viajes por scooter y filtra según el mínimo de viajes
    }

    public BigDecimal getTotalRevenue(int year, int startMonth, int endMonth) {
        // Llama a BillingService o TripService para obtener los ingresos por viaje y calcula el total
    }

    public ScooterStatusReportDTO getScooterStatusReport() {
        // Llama a ScooterService para contar los scooters en operación y en mantenimiento
        long operationalScooters = scooterClient.countOperationalScooters();
        long maintenanceScooters = scooterClient.countMaintenanceScooters();

        // Crear el DTO con la información
        return new ScooterStatusReportDTO(operationalScooters, maintenanceScooters);
    }

    public void adjustPrices(BigDecimal newPrice, LocalDate effectiveDate) {
        // Almacena el nuevo precio y su fecha de efectividad
    }

    public List<ScooterDTO> findNearbyScooters(Double latitude, Double longitude, Double radius) {
        // Llama a ScooterService para obtener los scooters dentro del radio especificado
    }


}
