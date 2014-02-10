package com.yahoo.mobileacademy.carpool.interfaces;

import com.parse.ParseObject;
import com.yahoo.mobileacademy.carpool.models.Ride;

/**
 * Interface that model should implement in order to be 
 * serializable as JSONObject and be friendly with the Parse API
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public interface ParseObjectSerializable {
	
	public ParseObject toParseObject();

}
