package org.tudai.scooterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudai.scooterservice.entity.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

}
