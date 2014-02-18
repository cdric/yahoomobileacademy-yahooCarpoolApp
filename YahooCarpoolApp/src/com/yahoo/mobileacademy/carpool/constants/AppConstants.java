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

	// Notification key for request to be added to an existing ride
	public static final int NOTIFICATION_TYPE_REQUEST_RIDE = 201;
   
}

