package org.tudai.tripservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tudai.tripservice.entitity.Pause;

@Repository
public interface PauseRepository extends JpaRepository<Pause, Long> {


}
