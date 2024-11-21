package org.tudai.reportservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tudai.reportservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.reportservice.dto.ScooterReportTripYearDTO;
import org.tudai.reportservice.dto.ScooterStatusReportDTO;
import org.tudai.reportservice.dto.ScooterUsageReportDTO;
import org.tudai.reportservice.feign.ScooterClient;
import org.tudai.reportservice.feign.TripClient;
import org.tudai.reportservice.feign.UserClient;
import org.tudai.reportservice.dto.ScooterDTO;
import org.tudai.reportservice.dto.TripDTO;

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
        return new ScooterUsageReportDTO(scooter.getId(), totalDistance, totalDuration);

    }

    public void disableAccount(Long accountId) {
        // Encuentra la cuenta por ID y marca su estado como inhabilitado
        userClient.updateAccountStatus(accountId, false);
    }

    public List<ScooterReportTripYearDTO> getScootersWithMoreThanXTrips(int year, int mintrips) {
        List<ScooterDTO> scooters = scooterClient.getAll();
        List<ScooterReportTripYearDTO> scoots = new ArrayList<>();
        for (ScooterDTO scooterDTO : scooters) {
            Long count = tripClient.countScooterTripByScooterAndYear(scooterDTO.getId(), year);
            if (count >= mintrips) {
                ScooterReportTripYearDTO scootDTO = new ScooterReportTripYearDTO(Math.toIntExact(scooterDTO.getId()), count, scooterDTO.getKilometersTraveled());
                scoots.add(scootDTO);
            }
        }
        return scoots;
    }

    public List<BenefitsBetweenMonthsDTO> getTotalRevenue(int year, int startMonth, int endMonth) {
        return tripClient.getBenefitsReport(year, startMonth, endMonth);
    }

    public ScooterStatusReportDTO getScooterStatusReport() {
        // Llama a ScooterService para contar los scooters en operación y en mantenimiento y lo muestra en dto.
        long operationalScooters = scooterClient.countOperationalScooters();
        long maintenanceScooters = scooterClient.countMaintenanceScooters();

        return new ScooterStatusReportDTO(operationalScooters, maintenanceScooters);
    }

    public List<ScooterDTO> findNearbyScooters(String ubicacion) {
        return scooterClient.getScootersByLocation(ubicacion);
    }

}
