package org.tudai.scooterservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.tudai.scooterservice.dto.StationDTO;

@FeignClient(name = "station-service")
public interface StationClient {

    @GetMapping("/stations/{stationId}")
    StationDTO getStationById(@PathVariable("stationId") Long stationId);

    @PutMapping("/stations/{stationId}/addScooter/{scooterId}")
    void addScooterToStation(@PathVariable("stationId") Long stationId, @PathVariable("scooterId") Long scooterId);

}
