package com.yahoo.mobileacademy.carpool.models;

import java.util.Date;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Model class for a Ride
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
@ParseClassName("Ride")
public class Ride extends ParseObject {
	
	// Empty Constructor
	public Ride() {
		super();
	}
	
	// Getters-Setters
	
	public Driver getDriver() {
		return (Driver) getParseObject("driver");
	}
	public void setDriver(Driver driver) {
		put("driver", driver);
	}
	public List<Passenger> getPassengers() {
		return getList("passengerList");
	}
	public void setPassengers(List<Passenger> list) {
		put("passengerList", list);
	}
	public Date getStartTime() {
		return getDate("startDate");
	}
	public void setStartTime(Date startTime) {
		put("startDate", startTime);
	}
	public String getDestination() {
		return getString("destination"); 
	}
	public void setDestination(String destination) {
		put("destination", destination);
	}
	public int getRideCapacity() {
		return getInt("vehiculeCapacity");
	}
	public void setRideCapacity(int rideCapacity) {
		put("vehiculeCapacity", rideCapacity);
	}
	
	/**
	 * Check if a ride is full
	 * 
	 * A ride is full only if it reached capacity with
	 * list of confirmed passengers.
	 * 
	 * @param r the Ride to check
	 * @return Returns TRUE is the ride if full, FALSE otherwise
	 */
	public static boolean isRideFull(Ride r) {
		
		int capacity = r.getRideCapacity();
		int nbPassengers = 0;
		
		if (r.getPassengers().isEmpty()) {
			nbPassengers = 0;
		} else {
			for (Passenger item : r.getPassengers()) {
				
				try {
					item.fetchIfNeeded(); //TODO: Blocking thread
					if (item.isApproved()) {
						nbPassengers++;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		return nbPassengers == capacity;
				
	}
	
	/**
	 * Add a new passenger to this ride
	 * @param p the passenger to add
	 */
	public void addPassenger(Passenger p) {
		List<Passenger> currentList = getPassengers();
		currentList.add(p);
		setPassengers(currentList);
	}
	
}
