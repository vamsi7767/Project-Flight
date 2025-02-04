package com.casestudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="flightsdata")
public class FlightModel {
	
	@Id
	public String flightNo;
	public String flightName;
	public String flightFrom;
	public String flightTo;
	public int fare;
	public int seats;
	public String time;
	public String date;
	
	public FlightModel() {
		super();
	}

	
	public FlightModel(String flightNo, String flightName, String flightFrom, String flightTo, int fare, int seats,
			String time) {
		super();
		this.flightNo = flightNo;
		this.flightName = flightName;
		this.flightFrom = flightFrom;
		this.flightTo = flightTo;
		this.fare = fare;
		this.seats = seats;
		this.time = time;
	}



	public String getflightNo() {
		return flightNo;
	}

	public void setflightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getflightName() {
		return flightName;
	}

	public void setflightName(String flightName) {
		this.flightName = flightName;
	}

	public String getflightFrom() {
		return flightFrom;
	}

	public void setflightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}

	public String getflightTo() {
		return flightTo;
	}

	public void setflightTo(String flightTo) {
		this.flightTo = flightTo;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "FlightModel [flightNo=" + flightNo + ", flightName=" + flightName + ", flightFrom="
				+ flightFrom + ", flightTo=" + flightTo + ", fare=" + fare + ", seats=" + seats + ", time=" + time + "]";
	}
	
	
}

