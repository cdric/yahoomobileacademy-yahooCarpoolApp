package com.yahoo.mobileacademy.carpool.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Abstract model class for a User
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class AuthenticatedUser {
	
	// Member variables
	
	private String name;
	private String email;
	private String phoneNumber;
	private String facebookId;

	// Getters-Setters
	
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

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public static AuthenticatedUser fromJSONObject(JSONObject jsonObject) {
		AuthenticatedUser user = new AuthenticatedUser();
		try {
			
			user.setName(jsonObject.getString("name"));
			user.setFacebookId(jsonObject.getString("facebookId"));
			
			// Not available through the Facebook Graph API
			user.setEmail(jsonObject.getString("first_name") + " AT facebook.com");
			user.setPhoneNumber("123-456-1234");
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

}
