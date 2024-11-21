package org.tudai.reportservice;

import org.tudai.reportservice.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tudai.reportservice.feign.ScooterClient;
import org.tudai.reportservice.feign.TripClient;
import org.tudai.reportservice.feign.UserClient;
import org.tudai.reportservice.service.ReportService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private TripClient tripClient;

    @Mock
    private ScooterClient scooterClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getScooterUsageReport_shouldCalculateUsage() {
        // Datos simulados
        ScooterDTO scooter = new ScooterDTO(1L, 500.0);
        TripDTO trip = new TripDTO(10.0, 5.0, 1.0);
        when(scooterClient.getAll()).thenReturn(List.of(scooter));
        when(tripClient.getTripsByScooterId(1L)).thenReturn(List.of(trip));

        // Ejecutar
        List<ScooterUsageReportDTO> report = reportService.getScooterUsageReport(false);

        // Verificar
        assertNotNull(report);
        assertEquals(1, report.size());
        assertEquals(1L, report.get(0).getScooterId());
        assertEquals(10.0, report.get(0).getTotalDistance());
        assertEquals(5.0, report.get(0).getTotalDuration());
    }

    @Test
    void disableAccount_shouldCallUserClient() {
        // Acción
        doNothing().when(userClient).updateAccountStatus(1L, false);

        // Ejecutar
        reportService.disableAccount(1L);

        // Verificar
        verify(userClient, times(1)).updateAccountStatus(1L, false);
    }

    @Test
    void getScootersWithMoreThanXTrips_shouldFilterScooters() {

        ScooterDTO scooter = new ScooterDTO(1L, 500.0);
        when(scooterClient.getAll()).thenReturn(List.of(scooter));
        when(tripClient.countScooterTripByScooterAndYear(1L, 2024)).thenReturn(10L);

        List<ScooterReportTripYearDTO> report = reportService.getScootersWithMoreThanXTrips(2024, 5);

        assertNotNull(report);
        assertEquals(1, report.size());
        assertEquals(1, report.get(0).getScooterId());
        assertEquals(10L, report.get(0).getTripCount());
    }

    @Test
    void getScooterStatusReport_shouldReturnCounts() {

        when(scooterClient.countOperationalScooters()).thenReturn(15L);
        when(scooterClient.countMaintenanceScooters()).thenReturn(3L);

        ScooterStatusReportDTO report = reportService.getScooterStatusReport();

        assertNotNull(report);
        assertEquals(15L, report.getOperationalScooters());
        assertEquals(3L, report.getMaintenanceScooters());
    }
}

