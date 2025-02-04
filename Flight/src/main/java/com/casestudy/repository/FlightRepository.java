package com.casestudy.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.model.FlightModel;

@Repository
public interface FlightRepository extends MongoRepository<FlightModel, String> {

	List<FlightModel> findByFlightFromAndFlightTo(String flightFrom, String flightTo, String date);

	List<FlightModel> findByFlightName(String flightName);

	FlightModel findByFlightNo(String flightNo);

	List<FlightModel> findByFlightFrom(String flightFrom);

	List<FlightModel> findByFlightTo(String flightTo);

	
}
