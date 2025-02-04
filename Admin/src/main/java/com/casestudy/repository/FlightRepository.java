package com.casestudy.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.casestudy.model.FlightModel;

@Repository
public interface FlightRepository extends MongoRepository<FlightModel, String> {

	 @Query("{ 'flightFrom' : ?0, 'flightTo' : ?1 }")
	FlightModel findByflightFromAndflightTo(String flightFrom, String flightTo);

	FlightModel findByflightName(String flightName);

	Optional<FlightModel> findByflightNo(String flightNo);
	
}
