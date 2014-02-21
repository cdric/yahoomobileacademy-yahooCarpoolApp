package com.yahoo.mobileacademy.carpool.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.activeandroid.util.Log;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class UtilityClass {
	
	/**
	 * Check if the internet conneciton is available
	 * @return Returns TRUE if internet is available, FALSE otherwise
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager CManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NInfo = CManager.getActiveNetworkInfo();
		if (NInfo != null && NInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return the authenticated user that is
	 * using this application
	 * @return
	 */
	public static AuthenticatedUser getAuthenticatedUser() {
		ParseUser pUser = ParseUser.getCurrentUser();
		AuthenticatedUser user = AuthenticatedUser.fromJSONObject(pUser.getJSONObject("profile"));
		return user;
	}

	// ----------------------
	// DATE RELATED FUNCTIONS
	// ----------------------
	
	/**
	 * Parse a Date String that match a given format
	 * @param format The format of the date to parse
	 * @param dateStr The date to parse
	 * @return Returns a Date object corresponding, or NULL if an exception occurs
	 */
	public static Date parseDate(String format, String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parse a String date that match a given format
	 * @param format The format of the date to parse
	 * @param dateStr The date to parse
	 * @return Returns a Date object corresponding, or NULL if an exception occurs
	 */
	public static String formatDate(String format, Date dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
			return formatter.format(dateStr);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Compute the difference between two dates 
	 * @param startTime the Start time
	 * @param endDate the End time
	 * @param format the format to return the result into
	 * @return Returns the difference following the format provided
	 */
	public static String computeDifferentBetweenTwoDates(Date startTime, Date endTime, PeriodFormatter formatter) {
		
		Period p = new Period(endTime.getTime(), startTime.getTime());
		return formatter.print(p);
		
	}

	// ------
	// OTHERS
	// ------
	
	/**
	 * Return the position of an element in an array (0...n)
	 * Return -1 if the element does not exist
	 * 
	 * @param element the element to look for
	 * @param array the array to parse
	 * @return
	 */
	public static int findValuePositionInArray(String element,
			String[] array) {
		
		if (array == null || array.length == 0) return -1;
		
		List<String> list = Arrays.asList(array);
		
		if (list.contains(element)) {
			return list.indexOf(element);
		} else {
			return -1;
		}
		
	}
	
	// ------------------------------
	// ANDROID VIEW RELATED FUNCTIONS
	// ------------------------------
	
	/**
	 * Initialize a spinner to have it's TextView to display text align to the right
	 * 
	 * @param context the Context
	 * @param spinner the Spinner instance that display 
	 * @param spinnerId the ref to the ID for the spinner 
	 * @param spinnerValues the ref to the ID of the array of resources for the spinner
	 * @return Returns the Spinner object
	 */
	public static Spinner initSpinnerWithRightTextAlignment(Context context, Spinner spinner, int spinnerValues) {

		// Update the layout of the spinner to align text to the right
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	    		context, 
	    		spinnerValues, 
	    		R.layout.spinner_textview);
	    spinner.setAdapter(adapter);
	    
	    return spinner;
	}

	/**
	 * Generate the URL to retrive the Facebook Profile Image
	 * @param userId the FacebookId to generate the URL for
	 */
	public static String getDisplayImageURLForFacebookId(String userId) {
		String image = "http://graph.facebook.com/"+ userId +"/picture?type=large";
		Log.d("debug", "getDisplayImageURLForFacebookId: " + image);
		return image;
	}
	
}
