package com.yahoo.mobileacademy.carpool.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Abstract model class for a User
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
@ParseClassName("User")
public abstract class User extends ParseObject {
	
	// Empty Constructor
	public User() {
		super(); 
	}
		
	// Getters-Setters
	
	public int getUserId() { 
		return getInt("userId");
	}

	public void setUserId(int id) {
		put("userId", id);
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public String getEmail() {
		return getString("email");
	}

	public void setEmail(String email) {
		put("email", email);
	}

	public String getPhoneNumber() {
		return getString("phoneNumber");
	}

	public void setPhoneNumber(String phoneNumber) {
		put("phoneNumber", phoneNumber);
	}

}
