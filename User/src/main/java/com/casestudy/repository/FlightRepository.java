package com.casestudy.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.model.FlightModel;

@Repository
public interface FlightRepository extends MongoRepository<FlightModel, String> {

	FlightModel findByFlightFromAndFlightTo(String FlightFrom, String FlightTo);

	FlightModel findByFlightName(String FlightName);

	Optional<FlightModel> findByFlightNo(String FlightNo);

	
}
