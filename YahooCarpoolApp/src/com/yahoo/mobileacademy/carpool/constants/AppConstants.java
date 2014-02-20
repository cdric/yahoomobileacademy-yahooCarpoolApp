package com.yahoo.mobileacademy.carpool.constants;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Constants for this application
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class AppConstants {
	
   // Code for the compose_activity	
   public static final int REQUEST_XXXXX_ACTIVITY = 101;
   
   // Date formatting
   public static final String DRIVER_RIDE_DATE_FORMAT = "h:mm a"; 
   
	public static final PeriodFormatter PERIODFORMATTER_HOURS_AND_MINUTES = new PeriodFormatterBuilder()
	    .appendHours()
	    .appendSuffix(" hour", " hours")
	    .appendSeparator(" and ")
	    .appendMinutes()
	    .appendSuffix(" minute", " minutes")
	    .toFormatter();

	// Notification keys
	// Request to be added to an existing ride
	public static final int NOTIFICATION_TYPE_REQUEST_RIDE = 201;
	// Ride has been accepted
	public static final int NOTIFICATION_TYPE_RIDE_ACCEPTED = 202;
	// Ride has been rejected
	public static final int NOTIFICATION_TYPE_RIDE_REJECTED = 203;
   
}

