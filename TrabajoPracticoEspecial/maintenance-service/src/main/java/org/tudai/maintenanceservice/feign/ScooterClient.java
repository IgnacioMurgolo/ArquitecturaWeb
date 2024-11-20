package org.tudai.maintenanceservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.tudai.maintenanceservice.dto.ScooterDTO;

@FeignClient(name = "scooter")
public interface ScooterClient {

    @GetMapping("/scooters/{id}")
    ScooterDTO findById(@PathVariable("id") Long id);

    @PatchMapping("/scooters/on-maintenance/{scooterId}")
    Void onMaintenance(@PathVariable("scooterId") Long scooterId);

    @PatchMapping("/scooters/end-maintenance/{scooterId}")
    Void endMaintenance(@PathVariable("scooterId") Long scooterId);

}
