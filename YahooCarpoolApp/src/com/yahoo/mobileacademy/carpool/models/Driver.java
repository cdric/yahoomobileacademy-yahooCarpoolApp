package com.yahoo.mobileacademy.carpool.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Model class for a Driver
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class Driver extends User {
	
	// Member variables
	private List<Passenger> passengersPendingApproval = new ArrayList<Passenger>();

	// Empty Constructor
	public Driver() {}
		
	// Getters-Setters
	
	public List<Passenger> getPassengersPendingApproval() {
		return passengersPendingApproval;
	}

	public void setPassengersPendingApproval(
			List<Passenger> passengersPendingApproval) {
		this.passengersPendingApproval = passengersPendingApproval;
	}

	public Object toJSONObject() {
		JSONObject result = new JSONObject();
		try {
			result.put("id", getId());
			result.put("name", getName());
			result.put("email", getEmail());
			result.put("phoneNumber", getPhoneNumber());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

	@Override
	public ParseObject toParseObject() {
		ParseObject user = super.toParseObject();
		ParseObject driver = new ParseObject("Driver");
		driver.put("passengersPendingApproval", getPassengersPendingApproval());
		driver.put("userRef", user);
		return driver;
	}
	
//	public Ride getRideDetails() {
//		return rideDetails;
//	}
//
//	public void setRideDetails(Ride rideDetails) {
//		this.rideDetails = rideDetails;
//	}
	
	
//	public static Driver fromJson(JSONObject jsonObject) {
//		Driver driver = new Driver();
//		//Ride ride = new Ride();
//		try {
//			
//			driver.setName(jsonObject.getString("name"));
//			driver.setEmail(jsonObject.getString("email"));
//			driver.setPhoneNumber(jsonObject.getString("phoneNumber"));
//			
////			JSONObject rideDetails = jsonObject.getJSONObject("rideDetails");
////			ride.setDestination(rideDetails.getString("destination"));
////			ride.setRideCapacity(rideDetails.getInt("capacity"));
////			ride.setStartTime((Date)rideDetails.get("startDate"));
//			//driver.setRideDetails(ride);
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return driver;
//	}
	

}
