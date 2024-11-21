package org.tudai.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tudai.reportservice.dto.ScooterDTO;

import java.util.List;

@FeignClient(name = "scooter-service")
public interface ScooterClient {

    @GetMapping("/scooters/")
    List<ScooterDTO> getAll();

    @GetMapping("/scooters/operationalScooters")
    Long countOperationalScooters();

    @GetMapping("/scooters/maintenanceScooters")
    Long countMaintenanceScooters();

    @GetMapping("/scooters/nearby")
    List<ScooterDTO> getScootersByLocation(@RequestParam("ubicacion") String ubicacion);
}
