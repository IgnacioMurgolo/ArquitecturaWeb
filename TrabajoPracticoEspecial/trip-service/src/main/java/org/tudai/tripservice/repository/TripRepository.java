package org.tudai.tripservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.entitity.Trip;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT new org.tudai.tripservice.dto.TripDTO(t.startDateTime, t.endDateTime, t.distanceTraveled, t.duration, t.creditsConsumed) FROM Trip t WHERE t.accountId=:account")
    List<TripDTO> getTripsByAccountId(Long account);


}
