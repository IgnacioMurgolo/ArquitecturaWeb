package org.tudai.scooterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudai.scooterservice.entity.Scooter;

import java.util.List;

@Repository
public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    long countByStatus(boolean status);

    List<Scooter> findByUbicationContainingIgnoreCase(String ubicacion);
}
