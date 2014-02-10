package com.yahoo.mobileacademy.carpool.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Model class for a Passenger
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class Passenger extends User {

	// Empty Constructor
	public Passenger() {}
	
	@Override
	public ParseObject toParseObject() {
		ParseObject user = super.toParseObject();
		ParseObject passenger = new ParseObject("Passenger");
		passenger.put("userRef", user);
		return passenger;
	}

}
