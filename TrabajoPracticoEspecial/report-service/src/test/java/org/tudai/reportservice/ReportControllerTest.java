package org.tudai.reportservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tudai.reportservice.controller.ReportController;
import org.tudai.reportservice.dto.ScooterReportTripYearDTO;
import org.tudai.reportservice.dto.ScooterStatusReportDTO;
import org.tudai.reportservice.dto.ScooterUsageReportDTO;
import org.tudai.reportservice.service.ReportService;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void getScooterUsageReport_shouldReturnReport() throws Exception {
        // Datos simulados
        ScooterUsageReportDTO report = new ScooterUsageReportDTO(1L, 100.0, 50.0);
        when(reportService.getScooterUsageReport(false)).thenReturn(List.of(report));

        // Realizar solicitud
        mockMvc.perform(get("/reports/scooter-usage")
                        .param("includePauseTime", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scooterId").value(1L))
                .andExpect(jsonPath("$[0].totalDistance").value(100.0))
                .andExpect(jsonPath("$[0].totalDuration").value(50.0));
    }

    @Test
    void disableAccount_shouldReturnOk() throws Exception {
        // Acción
        doNothing().when(reportService).disableAccount(1L);

        // Realizar solicitud
        mockMvc.perform(put("/reports/disable-account/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getScootersWithMinTrips_shouldReturnReport() throws Exception {
        // Datos simulados
        ScooterReportTripYearDTO report = new ScooterReportTripYearDTO(1, 10L, 500.0);
        when(reportService.getScootersWithMoreThanXTrips(2024, 5)).thenReturn(List.of(report));

        // Realizar solicitud
        mockMvc.perform(get("/reports/scooters-with-min-trips")
                        .param("year", "2024")
                        .param("minTrips", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scooterId").value(1))
                .andExpect(jsonPath("$[0].tripCount").value(10L))
                .andExpect(jsonPath("$[0].kilometersTraveled").value(500.0));
    }

    @Test
    void getScooterStatusReport_shouldReturnStatus() throws Exception {
        // Datos simulados
        ScooterStatusReportDTO statusReport = new ScooterStatusReportDTO(10L, 2L);
        when(reportService.getScooterStatusReport()).thenReturn(statusReport);

        // Realizar solicitud
        mockMvc.perform(get("/reports/scooter-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationalScooters").value(10L))
                .andExpect(jsonPath("$.maintenanceScooters").value(2L));
    }
}

