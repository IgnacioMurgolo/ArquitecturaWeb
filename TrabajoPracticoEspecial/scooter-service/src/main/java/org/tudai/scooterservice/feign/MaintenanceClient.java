package org.tudai.scooterservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "maintenance-service")
public interface MaintenanceClient {

    @GetMapping("/maintenance/scooter/{scooterId}")
    List<Long> getMaintenanceIdsByScooterId(@PathVariable("scooterId") Long scooterId);
}
