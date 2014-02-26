package com.yahoo.mobileacademy.carpool.asynctasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.SendCallback;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Notification;

/**
 * Retrieve a Driver object based on it's id
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class FetchDriverParseObjectDataAsyncTask extends AsyncTask<String, Void, Driver> {

	@Override
	protected Driver doInBackground(String... params) {

		Driver driver = null;
		String userId =  params[0];
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Driver");
		query.whereEqualTo("userId", userId);
		
		List<ParseObject> queryResult;
		
		try {
			queryResult = query.find(); // Blocking thread
			
		    if (queryResult.size() > 0) {
		    	
		    	// Assuming only one result
		    	driver = (Driver)queryResult.get(0);
		    	
		    } 
		    
		} catch (ParseException e) {
			e.printStackTrace();
		}
						
		return driver;
	}
	
	@Override
    protected void onPostExecute(final Driver result) {
		
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		String messageForRecipient = 
				authUser.getName() + " has requested to be part of your ride. Please review the list of pending guests for your ride to accept this user.";	
		
		// Create notification
		Notification notification = new Notification();
		notification.setFromUserId(authUser.getFacebookId());
		notification.setToUserId(result.getUserId());
		notification.setHasBeenRead(false);
		notification.setNotificationMessageForRecipient(messageForRecipient);
		notification.setNotificationType(AppConstants.NOTIFICATION_TYPE_REQUEST_RIDE);
		notification.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				
//				Toast.makeText(getContext(), "We sent a notification to " //+ driver.getUserId() 
//						+ "" + result.getName() + ""
// 						+ ". You should received a notification one the status of this ride.", Toast.LENGTH_SHORT).show();
				
			}
		});
		
//		// Send push notification to recipient
//		ParsePush push = new ParsePush();
//		push.setChannel("user-" + result.getUserId());
//		push.setMessage(messageForRecipient);
//		push.sendInBackground();
		
	}

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

}