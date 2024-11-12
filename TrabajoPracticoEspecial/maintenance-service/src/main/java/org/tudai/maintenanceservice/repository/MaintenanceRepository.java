package org.tudai.maintenanceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tudai.maintenanceservice.entity.Maintenance;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("SELECT m.id "+
           "FROM Maintenance m "+
           "WHERE m.scooterId = :scooterId")
    List<Long> getMaintenanceIdsByScooterId(@Param("scooterId")Long scooterId);
}
