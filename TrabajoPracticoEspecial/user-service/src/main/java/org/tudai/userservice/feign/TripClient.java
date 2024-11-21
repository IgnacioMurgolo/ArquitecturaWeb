package org.tudai.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.tudai.userservice.dto.TripDTO;

import java.util.List;

@FeignClient(name = "trip-service")
public interface TripClient {

    @GetMapping("/trips/account/{id}")
    List<TripDTO> getTripsByAccountId(@PathVariable("id") String id);
}
