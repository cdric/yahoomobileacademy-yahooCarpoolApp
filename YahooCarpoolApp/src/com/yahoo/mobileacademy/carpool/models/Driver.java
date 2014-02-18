package com.yahoo.mobileacademy.carpool.models;

import java.util.List;

import com.parse.ParseClassName;

/**
 * Model class for a Driver
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
@ParseClassName("Driver")
public class Driver extends User {
	
	// Empty Constructor
	public Driver() {
		super();
	}
		
	// Getters-Setters
	
	public List<Passenger> getPassengersPendingApproval() {
		return getList("passengersPendingApproval");
	}

	public void setPassengersPendingApproval(
			List<Passenger> passengersPendingApproval) {
		put("passengersPendingApproval", passengersPendingApproval);
	}

}
