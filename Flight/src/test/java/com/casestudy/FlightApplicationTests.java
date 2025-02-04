package com.casestudy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.casestudy.controller.FlightController;
import com.casestudy.model.FlightModel;
import com.casestudy.repository.FlightRepository;

@SpringBootTest
class FlightApplicationTests {
	
	 @Mock
	    private FlightRepository flightRepository;

	    @Mock
	    private Logger logger;

	    @InjectMocks
	    private FlightController flightController;

	    @Captor
	    private ArgumentCaptor<String> flightFromCaptor;

	    @Captor
	    private ArgumentCaptor<String> flightToCaptor;
	    
	    @Captor
	    private ArgumentCaptor<FlightModel> flightModelCaptor;
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @Test
		void contextLoads() {
		}


	    @Test
	    public void testFindByloc() {
	        // Mocking the flight repository's behavior
	        List<FlightModel> flights = Arrays.asList(
	                new FlightModel("1", "Flight 1", "Airport A", "Airport B", 100, 50, "10:00 AM","22-06-2023"),
	                new FlightModel("2", "Flight 2", "Airport B", "Airport C", 150, 30, "12:00 PM","22-06-2023"));
	        when(flightRepository.findByFlightFromAndFlightTo("Airport A", "Airport B", "22-06-2023")).thenReturn(flights);

	        // Calling the controller method
	        List<FlightModel> result = flightController.findByloc("Airport A", "Airport B","22-06-2023");

	        // Verifying the repository method was called with the correct arguments
	        verify(flightRepository).findByFlightFromAndFlightTo("Airport A", "Airport B", "22-06-2023");

	        // Verifying the returned result
	        assertEquals(flights, result);
	    }
	    @Test
	    public void testAddFlight() {
	        // Creating a sample FlightModel object
	        FlightModel flightModel = new FlightModel("ABC123", "Flight 1", "Airport A", "Airport B", 100, 50, "10:00", "22--6-2023");

	        // Calling the controller method
	        String result = flightController.addflight(flightModel);

	        // Verifying that the repository's save method was called with the correct FlightModel argument
	        verify(flightRepository).save(flightModelCaptor.capture());
	        FlightModel capturedFlightModel = flightModelCaptor.getValue();
	        assertEquals(flightModel, capturedFlightModel);

	        // Verifying the returned result
	        assertEquals("Flight Added Successfully", result);
	    }
 	}

	


