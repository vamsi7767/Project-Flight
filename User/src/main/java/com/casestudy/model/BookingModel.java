package com.casestudy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ticket")
public class BookingModel {
	
	@Id
	private String pnrId;
	private String userId;
	private String name;
	private String phnnumber;
	private String email;
	private String flightNo;
	private String flightName;
	private String flightFrom;
	private String flightTo;
	private String date;
	private String time;
	private int totalseats;
	private int fare;
	
	public BookingModel() {
		super();
	}
	
	public BookingModel(String pnrId, String userId, String name, String phnnumber, String email, String flightNo,
			String flightName, String flightFrom, String flightTo, String date, String time, int totalseats, int fare) {
		super();
		this.pnrId = pnrId;
		this.userId = userId;
		this.name = name;
		this.phnnumber = phnnumber;
		this.email = email;
		this.flightNo = flightNo;
		this.flightName = flightName;
		this.flightFrom = flightFrom;
		this.flightTo = flightTo;
		this.date = date;
		this.time = time;
		this.totalseats = totalseats;
		this.fare = fare;
	}
	
	
	public String getPnrId() {
		return pnrId;
	}
	
	public void setPnrId(String pnrId) {
		this.pnrId = pnrId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhnnumber() {
		return phnnumber;
	}
	
	public void setPhnnumber(String phnnumber) {
		this.phnnumber = phnnumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getTotalseats() {
		return totalseats;
	}
	
	public void setTotalseats(int totalseats) {
		this.totalseats = totalseats;
	}
	
	public int getFare() {
		return fare;
	}
	
	public void setFare(int fare) {
		this.fare = fare;
	}
	
	@Override
	public String toString() {
		return "BookingModel [pnrId=" + pnrId + ", userId=" + userId + ", name=" + name + ", phnnumber=" + phnnumber
				+ ", email=" + email + ", flightNo=" + flightNo + ", flightName=" + flightName + ", flightFrom=" + flightFrom
				+ ", flightTo=" + flightTo + ", date=" + date + ", time=" + time + ", totalseats=" + totalseats
				+ ", fare=" + fare + "]";
	}
	
	
}
