package com.yahoo.mobileacademy.carpool.models;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseClassName;

/**
 * Model class for a Passenger
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
@ParseClassName("Passenger")
public class Passenger extends User {

	// Provide an convenient way to setup a Ride with an empty list of passengers
	public static final List<Passenger> EMPTY_VEHICULE = new ArrayList<Passenger>();

	// Empty Constructor
	public Passenger() {
		super();
	}
	
	public boolean isApproved() {
		return getBoolean("hasBeenApproved");
	}
	
	public void setIsApproved(boolean status) {
		put("hasBeenApproved", status);
	}
	
}
