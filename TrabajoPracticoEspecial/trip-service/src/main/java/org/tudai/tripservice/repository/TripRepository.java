package org.tudai.tripservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tudai.tripservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.entitity.Trip;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT new org.tudai.tripservice.dto.TripDTO(t.startDateTime, t.endDateTime, t.distanceTraveled, t.duration, t.creditsConsumed, t.accountId, t.scooterId) FROM Trip t WHERE t.accountId=:account")
    List<TripDTO> getTripsByAccountId(Long account);


    @Query("SELECT COUNT(t) FROM Trip t WHERE t.scooterId = :scooterId " +
            "AND FUNCTION('YEAR', t.startDateTime) = :year")
    Long countTripByScooterAndYear(@Param("scooterId") Long scooterId, @Param("year") int year);

    @Query("SELECT new org.tudai.tripservice.dto.BenefitsBetweenMonthsDTO(MONTH(t.startDateTime),SUM(t.creditsConsumed)) " +
            "FROM Trip t " +
            "WHERE MONTH(t.startDateTime) BETWEEN :startMonth AND :endMonth AND YEAR(t.startDateTime)=:year " +
            "GROUP BY MONTH(t.startDateTime) ")
    List<BenefitsBetweenMonthsDTO> getBenefitsReport(@Param("year") int year, @Param("startMonth") int startMonth, @Param("endMonth") int endMonth);
}
