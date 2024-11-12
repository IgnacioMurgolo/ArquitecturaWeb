package org.tudai.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.tudai.scooterservice.dto.ScooterDTO;

import java.util.List;

@FeignClient(name = "scooter-service")
public interface ScooterClient {

    @GetMapping("/")
    List<ScooterDTO> getAll();

    @GetMapping("/operationalScooters")
    Long countOperationalScooters();

    @GetMapping("/maintenanceScooters")
    Long countMaintenanceScooters();
}
