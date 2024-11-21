package org.tudai.reportservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tudai.reportservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.reportservice.dto.ScooterReportTripYearDTO;
import org.tudai.reportservice.dto.ScooterStatusReportDTO;
import org.tudai.reportservice.dto.ScooterUsageReportDTO;
import org.tudai.reportservice.service.ReportService;
import org.tudai.reportservice.dto.ScooterDTO;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // a. Reporte de uso de monopatines por kilómetros (con opción para incluir tiempos de pausa)
    @GetMapping("/scooter-usage")
    public ResponseEntity<List<ScooterUsageReportDTO>> getScooterUsageReport(
            @RequestParam(required = false, defaultValue = "false") boolean includePauseTime) {
        try {
            List<ScooterUsageReportDTO> report = reportService.getScooterUsageReport(includePauseTime);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // b. Anular una cuenta (inhabilitar temporalmente)
    @PutMapping("/disable-account/{accountId}")
    public ResponseEntity<Void> disableAccount(@PathVariable Long accountId) {
        reportService.disableAccount(accountId);
        return ResponseEntity.ok().build();
    }

    // c. Consultar monopatines con más de X viajes en un cierto año
    @GetMapping("/scooters-with-min-trips")
    public ResponseEntity<List<ScooterReportTripYearDTO>> getScootersWithMinTrips(
            @RequestParam int year,
            @RequestParam int minTrips) {
        try{
            List<ScooterReportTripYearDTO> report = reportService.getScootersWithMoreThanXTrips(year, minTrips);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // d. Consultar el total facturado en un rango de meses de un año
    @GetMapping("/total-revenue")
    public List<BenefitsBetweenMonthsDTO> getTotalRevenue(
            @RequestParam int year,
            @RequestParam int startMonth,
            @RequestParam int endMonth) {
        return reportService.getTotalRevenue(year, startMonth, endMonth);
    }

    // e. Consultar la cantidad de monopatines en operación versus en mantenimiento
    @GetMapping("/scooter-status")
    public ResponseEntity<ScooterStatusReportDTO> getScooterStatusReport() {
        try {
             ScooterStatusReportDTO reportDTO = reportService.getScooterStatusReport();
            return ResponseEntity.ok(reportDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // f. Ajuste de precios para habilitar a partir de cierta fecha
    //   ESTA DESDE ADMIN

    // g. Listado de monopatines cercanos a una ubicación
    @GetMapping("/nearby-scooters")
    public List<ScooterDTO> findNearbyScooters(
            @RequestParam String ubicacion
            ) {
        return reportService.findNearbyScooters(ubicacion);
    }
}

