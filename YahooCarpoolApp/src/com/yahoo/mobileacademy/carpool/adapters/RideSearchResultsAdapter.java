package com.yahoo.mobileacademy.carpool.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Notification;
import com.yahoo.mobileacademy.carpool.models.Passenger;
import com.yahoo.mobileacademy.carpool.models.Ride;

/**
 * Custom adapter for the Search result list view
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class RideSearchResultsAdapter extends ArrayAdapter<Ride> {
	
	public RideSearchResultsAdapter(Context context, List<Ride> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View mView, ViewGroup parent) {
		
		if (mView == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflatter.inflate(R.layout.listview_search_results, null);

		}

		final Ride ride = getItem(position);  
		
		final TextView nameView = (TextView) mView.findViewById(R.id.tvName);
		final TextView startTime = (TextView) mView.findViewById(R.id.tvStartTime);
		final ImageView imageView = (ImageView) mView.findViewById((R.id.ivProfile));
		final TextView spaceLeft = (TextView) mView.findViewById(R.id.tvSpaceLeft);
		final Button btnRequestRide = (Button) mView.findViewById(R.id.btnRequestRide);
		
		ride.getDriver().fetchIfNeededInBackground(new GetCallback<Driver>() {
			
	        public void done(Driver driver, ParseException e) {
	        	
	            String formattedName = "<b>" + driver.getName() + "</b>";
	            nameView.setText(Html.fromHtml(formattedName));   	      
	    		
	    		Date rideStartTime = ride.getStartTime(); 
	    		String formattedStartTime = "<i>Leaving at: </i>" + UtilityClass.formatDate(AppConstants.DRIVER_RIDE_DATE_FORMAT, rideStartTime)
	    				+ "<br>(in " + UtilityClass.computeDifferentBetweenTwoDates(new Date(), rideStartTime, AppConstants.PERIODFORMATTER_HOURS_AND_MINUTES)+ ")";
	    		startTime.setText(Html.fromHtml(formattedStartTime));  
	    		
	    		ImageLoader.getInstance().displayImage(UtilityClass.getDisplayImageURLForFacebookId(driver.getUserId()), imageView);
	    		
	    		String formattedSpaceLeft = null;
	    		if (Ride.isRideFull(ride)) {
	    			formattedSpaceLeft = "<b>This ride is already full</b>";
	    		} else {
	        		if (ride.getPassengers() != null) {
	        			formattedSpaceLeft = "<i>Vehicule capacity:</i> " + ride.getPassengers().size() + " / " + ride.getRideCapacity();
	        		} else {
	        			formattedSpaceLeft = "<i>Vehicule capacity:</i> 0 / " + ride.getRideCapacity();
	        		}
	    		}
	    		spaceLeft.setText(Html.fromHtml(formattedSpaceLeft));
	    		
	    		// Storing the userId inside the imageView Tag property
	    		btnRequestRide.setTag(driver.getUserId()); 
	    		
	    		if (Ride.isRideFull(ride)) {
	    			btnRequestRide.setEnabled(false);
	    		} else {
	    			// Register onClickListenerEvent
	    			btnRequestRide.setEnabled(true);
	    			btnRequestRide.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							// Fetch the userId for this search result
							int userId = (Integer) v.getTag();
							new FetchDriverParseObject().execute(userId);
							
							btnRequestRide.setEnabled(false);
							
							// Update with you as a potential passenger
							
							AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
							
							// Create Passenger object
							Passenger passenger = new Passenger();
							passenger.setUserId(authUser.getFacebookId());
							passenger.setName(authUser.getName()); // TODO: We should remove this and get it from the 
							                               // Facebook API if possible as part of the user profile
							passenger.setIsApproved(false);
							
							ride.addPassenger(passenger);
							ride.saveInBackground(new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									
									Toast.makeText(getContext(), "Your request has been successfully saved", Toast.LENGTH_SHORT).show();
									
								}
							});
							
						}
					});
	    		}
	        }
	        
	    });
		
		return mView;
	}
	
	/**
	 * Retrieve a Driver object based on it's id
	 * @author CŽdric Lignier <cedric.lignier@free.fr>
	 *
	 */
	private class FetchDriverParseObject extends AsyncTask<Integer, Void, Driver> {

		private Driver driver;
		private int userId;
		
		@Override
		protected Driver doInBackground(Integer... params) {

			driver = null;
			userId =  params[0];
			
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
	    protected void onPostExecute(Driver result) {
			
			AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
			String messageForRecipient = 
					authUser.getName() + " has requested to be part of your ride. Please review the list of pending guests for your ride to accept this user.";	
			
			// Create notification
			Notification notification = new Notification();
			notification.setFromUserId(authUser.getFacebookId());
			notification.setToUserId(Integer.valueOf(driver.getUserId()));
			notification.setHasBeenRead(false);
			notification.setNotificationMessageForRecipient(messageForRecipient);
			notification.setNotificationType(AppConstants.NOTIFICATION_TYPE_REQUEST_RIDE);
			notification.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					
					Toast.makeText(getContext(), "We sent a notification to " //+ driver.getUserId() 
							+ "" + driver.getName() + ""
	 						+ ". You should received a notification one the status of this ride.", Toast.LENGTH_SHORT).show();
					
				}
			});
			
		}

	    @Override
	    protected void onPreExecute() {}

	    @Override
	    protected void onProgressUpdate(Void... values) {}

	}

}
