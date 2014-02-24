package com.yahoo.mobileacademy.carpool.adapters.models;

import java.util.Date;

import com.yahoo.mobileacademy.carpool.models.Ride;

public class RideSearchResult {
	
	private String driverUserId;
	private String profileImage;
	private String driverName;
	private Date rideStartTime;
	private String rideDepartureETA;
	private String vehiculeCapacityLeft;
	private boolean isRideFull;
	private boolean hasPassengerRequestSent = false;
	private boolean hasPassengerRequestBeenApproved = false;
	
	private Ride ride;
	
	public RideSearchResult() { }

	/**
	 * @return the profileImage
	 */
	public String getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage the profileImage to set
	 */
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the rideStartTime
	 */
	public Date getRideStartTime() {
		return rideStartTime;
	}

	/**
	 * @param date the rideStartTime to set
	 */
	public void setRideStartTime(Date date) {
		this.rideStartTime = date;
	}

	/**
	 * @return the rideDepartureETA
	 */
	public String getRideDepartureETA() {
		return rideDepartureETA;
	}

	/**
	 * @param rideDepartureETA the rideDepartureETA to set
	 */
	public void setRideDepartureETA(String rideDepartureETA) {
		this.rideDepartureETA = rideDepartureETA;
	}

	/**
	 * @return the vehiculeCapacityLeft
	 */
	public String getVehiculeCapacityLeft() {
		return vehiculeCapacityLeft;
	}

	/**
	 * @param vehiculeCapacityLeft the vehiculeCapacityLeft to set
	 */
	public void setVehiculeCapacityLeft(String vehiculeCapacityLeft) {
		this.vehiculeCapacityLeft = vehiculeCapacityLeft;
	}
	
	/**
	 * @return the driverUserId
	 */
	public String getDriverUserId() {
		return driverUserId;
	}

	/**
	 * @param driverUserId the driverUserId to set
	 */
	public void setDriverUserId(String driverUserId) {
		this.driverUserId = driverUserId;
	}

	/**
	 * @return the isRideFull
	 */
	public boolean isRideFull() {
		return isRideFull;
	}

	/**
	 * @param isRideFull the isRideFull to set
	 */
	public void setRideFull(boolean isRideFull) {
		this.isRideFull = isRideFull;
	}

	/**
	 * @return the ride
	 */
	public Ride getRide() {
		return ride;
	}

	/**
	 * @param ride the ride to set
	 */
	public void setRide(Ride ride) {
		this.ride = ride;
	}

	/**
	 * @return the hasPassengerRequestSent
	 */
	public boolean isHasPassengerRequestSent() {
		return hasPassengerRequestSent;
	}

	/**
	 * @param hasPassengerRequestSent the hasPassengerRequestSent to set
	 */
	public void setHasPassengerRequestSent(boolean hasPassengerRequestSent) {
		this.hasPassengerRequestSent = hasPassengerRequestSent;
	}

	/**
	 * @return the hasPassengerRequestBeenApproved
	 */
	public boolean isHasPassengerRequestBeenApproved() {
		return hasPassengerRequestBeenApproved;
	}

	/**
	 * @param hasPassengerRequestBeenApproved the hasPassengerRequestBeenApproved to set
	 */
	public void setHasPassengerRequestBeenApproved(
			boolean hasPassengerRequestBeenApproved) {
		this.hasPassengerRequestBeenApproved = hasPassengerRequestBeenApproved;
	}

}
