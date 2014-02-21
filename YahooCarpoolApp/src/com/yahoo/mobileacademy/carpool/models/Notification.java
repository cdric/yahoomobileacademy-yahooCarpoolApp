package com.yahoo.mobileacademy.carpool.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Abstract model class for a Notification event
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
@ParseClassName("Notification")
public class Notification extends ParseObject {
	
	// Empty Constructor
	public Notification() {
		super();
	}
		
	// Getters-Setters
	
	public String getFromUserId() {
		return getString("fromUserId");
	}

	public void setFromUserId(String id) {
		put("fromUserId", id);
	}
	
	public String getToUserId() {
		return getString("toUserId");
	}

	public void setToUserId(String userId) {
		put("toUserId", userId);
	}
	
	public int getNotificationType() {
		return getInt("notificationKey");
	}
	
	public void setNotificationType(int key) {
		put("notificationKey", key);
	}
	
	public String getNotificationMessage() {
		return getString("notificationMessage");
	}
	
	public void setNotificationMessageForRecipient(String message) {
		put("notificationMessage", message);
	}
	
	public boolean hasBeenRead() {
		return getBoolean("hasBeenRead");
	}
	
	public void setHasBeenRead(boolean hasBeenRead) {
		put("hasBeenRead", hasBeenRead);
	}

}
