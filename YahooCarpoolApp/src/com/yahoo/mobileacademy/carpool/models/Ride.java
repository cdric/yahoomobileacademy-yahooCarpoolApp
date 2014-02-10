package com.yahoo.mobileacademy.carpool.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseObject;
import com.yahoo.mobileacademy.carpool.interfaces.ParseObjectSerializable;

/**
 * Model class for a Ride
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class Ride implements ParseObjectSerializable {
	
	// Member variables
	
	private Driver driver;
	private List<Passenger> passengers = new ArrayList<Passenger>();
	private Date startTime;
	private String destination;
	private int rideCapacity;
	
	// Empty Constructor
	public Ride() {}
	
	// Getters-Setters
	
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Object> list) {
		List<Passenger> result = new ArrayList<Passenger>();
		for (Object item : list) {
			result.add((Passenger) item);
		}
		this.passengers = new ArrayList<Passenger>(result);
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getRideCapacity() {
		return rideCapacity;
	}
	public void setRideCapacity(int rideCapacity) {
		this.rideCapacity = rideCapacity;
	}
	
	@Override
	public ParseObject toParseObject() {

		ParseObject ride = new ParseObject("Ride");
		ride.put("vehiculeCapacity", getRideCapacity());
		ride.put("destiantion", getDestination());
		ride.put("startDate", getStartTime());
		ride.put("passengerList", getPassengers());
		return ride;
		
	}
	
	public static Ride fromParseObject(ParseObject po) {

		Ride ride = new Ride();
		ride.setDestination(po.getString("destiantion"));
		//ride.setDriver(po.getJSONObject("driver"));
		ride.setPassengers( po.getList("passengerList") );
		ride.setStartTime(po.getDate("startDate"));
		ride.setRideCapacity(po.getInt("vehiculeCapacity"));
		
		return ride;
		
	}
	
}
