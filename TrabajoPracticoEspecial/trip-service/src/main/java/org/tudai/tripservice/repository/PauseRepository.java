package org.tudai.tripservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tudai.tripservice.entitity.Pause;

@Repository
public interface PauseRepository extends MongoRepository<Pause, String> {


}
