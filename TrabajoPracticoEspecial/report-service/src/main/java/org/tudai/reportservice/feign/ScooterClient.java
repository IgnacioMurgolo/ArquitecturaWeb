package org.tudai.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tudai.scooterservice.dto.ScooterDTO;
import org.tudai.scooterservice.dto.ScooterReportDTO;

import java.util.List;

@FeignClient(name = "scooter-service")
public interface ScooterClient {

    @GetMapping("/")
    List<ScooterDTO> getAll();

    @GetMapping
    List<ScooterReportDTO> getAllReport();

    @GetMapping("/operationalScooters")
    Long countOperationalScooters();

    @GetMapping("/maintenanceScooters")
    Long countMaintenanceScooters();

    @GetMapping("/scooters/nearby")
    List<ScooterDTO> getScootersByLocation(@RequestParam("ubicacion") String ubicacion);
}
