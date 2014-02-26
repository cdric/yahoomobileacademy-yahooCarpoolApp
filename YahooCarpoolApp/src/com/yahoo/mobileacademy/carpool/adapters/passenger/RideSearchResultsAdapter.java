package com.yahoo.mobileacademy.carpool.adapters.passenger;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

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
import com.yahoo.mobileacademy.carpool.adapters.models.RideSearchResult;
import com.yahoo.mobileacademy.carpool.asynctasks.FetchDriverParseObjectDataAsyncTask;
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
public class RideSearchResultsAdapter extends ArrayAdapter<RideSearchResult> {
	
	public RideSearchResultsAdapter(Context context, List<RideSearchResult> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View mView, ViewGroup parent) {
		
		if (mView == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflatter.inflate(R.layout.listview_search_results, null);

		}

		final RideSearchResult result = getItem(position);  
		
		TextView nameView = (TextView) mView.findViewById(R.id.tvName);
		TextView startTime = (TextView) mView.findViewById(R.id.tvStartTime);
		ImageView imageView = (ImageView) mView.findViewById((R.id.ivProfile));
		TextView spaceLeft = (TextView) mView.findViewById(R.id.tvSpaceLeft);
		final Button btnRequestRide = (Button) mView.findViewById(R.id.btnRequestRide);
			        	
        String formattedName = "<b>" + result.getDriverName() + "</b>";
        nameView.setText(Html.fromHtml(formattedName));   	      
		
		Date rideStartTime = result.getRideStartTime(); 
		String formattedStartTime = "<i>Leaving at: </i>" + UtilityClass.formatDate(AppConstants.DRIVER_RIDE_DATE_FORMAT, rideStartTime)
				+ "<br>(in " + UtilityClass.computeDifferentBetweenTwoDates(DateTime.now().toDate(), rideStartTime, AppConstants.PERIODFORMATTER_HOURS_AND_MINUTES)+ ")";
		startTime.setText(Html.fromHtml(formattedStartTime));  
		
		ImageLoader.getInstance().displayImage(UtilityClass.getDisplayImageURLForFacebookId(result.getDriverUserId()), imageView);
		
		String formattedSpaceLeft = null;
		if (result.isRideFull()) {
			formattedSpaceLeft = "<b>This ride is already full</b>";
		} else {
    		if (result.getRide().getPassengers() != null) {
    			formattedSpaceLeft = "<i>Vehicule capacity:</i> " + result.getRide().getPassengers().size() + " / " + result.getRide().getRideCapacity();
    		} else {
    			formattedSpaceLeft = "<i>Vehicule capacity:</i> 0 / " + result.getRide().getRideCapacity();
    		}
		}
		spaceLeft.setText(Html.fromHtml(formattedSpaceLeft));
		
		// Storing the userId inside the imageView Tag property
		btnRequestRide.setTag(result.getDriverUserId()); 
		
		if (result.isRideFull()) {
			btnRequestRide.setEnabled(false);
			btnRequestRide.setText("Ride full"); 
		} else {
			
			if (result.isHasPassengerRequestSent()) {
				
				btnRequestRide.setEnabled(false);
				
				if (result.isHasPassengerRequestBeenApproved()) {
					
					btnRequestRide.setText("Req. approved");
					
				} else {
				
					btnRequestRide.setText("Req. pending");
				
				}
				
			} else {
			
				// Register onClickListenerEvent
				btnRequestRide.setEnabled(true);
				btnRequestRide.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						// Fetch the userId for this search result
						String userId = (String) v.getTag();
						new FetchDriverParseObjectDataAsyncTask().execute(userId);
						
						btnRequestRide.setEnabled(false);
						
						// Update with you as a potential passenger
						
						AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
						
						// Create Passenger object
						Passenger passenger = new Passenger();
						passenger.setUserId(authUser.getFacebookId());
						passenger.setName(authUser.getName()); // TODO: We should remove this and get it from the 
						                               // Facebook API if possible as part of the user profile
						passenger.setIsApproved(false);
						
						result.getRide().addPassenger(passenger);
						result.getRide().saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								
								//Toast.makeText(getContext(), "Your request has been successfully saved", Toast.LENGTH_SHORT).show();
								result.setHasPassengerRequestSent(true);
								notifyDataSetChanged();
								
							}
						});
						
					}
				});
			
			}
		}
		
		return mView;
	}

}
