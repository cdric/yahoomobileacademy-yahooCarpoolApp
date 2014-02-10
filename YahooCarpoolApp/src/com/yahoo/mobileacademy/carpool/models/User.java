package com.yahoo.mobileacademy.carpool.models;

import org.json.JSONObject;

import com.parse.ParseObject;
import com.yahoo.mobileacademy.carpool.interfaces.ParseObjectSerializable;

/**
 * Abstract model class for a User
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public abstract class User implements ParseObjectSerializable {
	
	// Member variables
	private String name;
	private String id;
	private String email;
	private String phoneNumber;

	// Empty Constructor
	public User() {}
		
	// Getters-Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override 
	public ParseObject toParseObject() {
		ParseObject user = new ParseObject("User");
		user.put("id", getId());
		user.put("name", getName());
//		user.put("phoneNumber", getPhoneNumber());
//		user.put("email", getEmail());
		return user;
	}
	
	public static User fromJSONObject(JSONObject jsonObject) {
		//TODO
		return null;
	}

}
