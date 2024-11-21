package org.tudai.tripservice.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.tudai.tripservice.entitity.Trip;

import java.util.List;

public interface TripRepository extends MongoRepository<Trip, String>, CustomTripRepository {

    List<Trip> findTripByAccountId(String accountId);

    @Aggregation(pipeline = {
            "{ '$match': { " +
                    "  'scooterId': ?0, " +
                    "  '$expr': { '$eq': [ { '$year': '$startDateTime' }, ?1 ] } " +
                    "} }",
            "{ '$count': 'count' }"
    })
    Long countTripByScooterAndYear(Long scooterId, int year);


}
