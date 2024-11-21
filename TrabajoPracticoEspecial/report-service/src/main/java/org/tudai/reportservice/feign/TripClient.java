package org.tudai.reportservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.tudai.reportservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.reportservice.dto.TripDTO;
import java.util.List;

@FeignClient(name = "trip-service")
public interface TripClient {

    @GetMapping("/trips/scooter/{scooterId}")
    List<TripDTO> getTripsByScooterId(@PathVariable("scooterId") Long scooterId);

    @GetMapping("/trips/countByScooterAndYear")
    Long countScooterTripByScooterAndYear(@RequestParam Long scooterId, @RequestParam int year);

    @GetMapping("/trips/benefitsByYearBetweenMonth")
    List<BenefitsBetweenMonthsDTO> getBenefitsReport(@RequestParam int year, @RequestParam int startMonth, @RequestParam int endMonth);

}

