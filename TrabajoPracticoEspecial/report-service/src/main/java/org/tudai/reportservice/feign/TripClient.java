package org.tudai.reportservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.tudai.reportservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.tripservice.dto.TripDTO;

import java.util.List;

@FeignClient(name = "trip-service")
public interface TripClient {

    @GetMapping("/trips/scooter/{scooterId}")
    List<TripDTO> getTripsByScooterId(@PathVariable("scooterId") Long scooterId);


    @GetMapping("/trips/countByScooterAndYear")
    Long countScooterTripByScooterAndYear(@RequestParam Long scooterId, @RequestParam int year);

    @GetMapping("/trips/")
    List<BenefitsBetweenMonthsDTO> getBenefitsReport(int year, int startMonth, int endMonth);
}

