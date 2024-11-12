package org.tudai.scooterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tudai.scooterservice.entity.Scooter;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    long countByStatus(boolean status);

}
