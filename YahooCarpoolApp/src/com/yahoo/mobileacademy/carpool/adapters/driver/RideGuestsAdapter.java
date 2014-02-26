package com.yahoo.mobileacademy.carpool.adapters.driver;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Notification;
import com.yahoo.mobileacademy.carpool.models.Passenger;
import com.yahoo.mobileacademy.carpool.models.Ride;

/**
 * Custom adapter for the Passenger list view
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class RideGuestsAdapter extends ArrayAdapter<Passenger> {
	
	// Important note, only make class constant variable
	// that are not result dependent
	
	private RideGuestsAdapter mAdapter;
	private Ride mRide;
	
	public RideGuestsAdapter(Context context, Ride ride) {
		super(context, 0, ride.getPassengers());
		mRide = ride;
	}
	
	@Override
	public View getView(int position, View mView, ViewGroup parent) {
		mAdapter = this;
		
		if (mView == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflatter.inflate(R.layout.listview_ride_guests, null);

		}

		final Passenger mPassenger = getItem(position);

		TextView nameView = (TextView) mView.findViewById(R.id.tvName);
		String formattedName = "<b>" + mPassenger.getName() + "</b>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView emailView = (TextView) mView.findViewById(R.id.tvEmail);
		String formattedEmail;
		if (mPassenger.getEmail() != null) {
		    formattedEmail = mPassenger.getEmail();
		} else {
			//TODO: FIX THIS WITH REAL IMPLEM
			//formattedEmail = "This user has not provided an email address";
			formattedEmail = mPassenger.getName().replace(" ", ".").toLowerCase() + "@yahoo.com";
		}
		emailView.setText(Html.fromHtml(formattedEmail));
		
		TextView phoneView = (TextView) mView.findViewById(R.id.tvPhone);
		String formattedPhone;
		if (mPassenger.getPhoneNumber() != null) {
			formattedPhone = mPassenger.getPhoneNumber(); 
		} else {
			//TODO: FIX THIS WITH REAL IMPLEM
			//formattedPhone = "This user has not provided a phone number to be reached out to";
			Random rand = new Random(mPassenger.getName().length()); // Fixed number generator / user
			formattedPhone = "415-" + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + "-" 
					+ rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9) + rand.nextInt(9);
		}
		phoneView.setText(Html.fromHtml(formattedPhone));
		
		ImageView imageView = (ImageView) mView.findViewById((R.id.ivProfile));
		ImageLoader.getInstance().displayImage(
				UtilityClass.getDisplayImageURLForFacebookId(mPassenger.getUserId()), imageView);

		ImageButton btnAccept = (ImageButton) mView.findViewById(R.id.btn_accept_ride);
		ImageButton btnReject = (ImageButton) mView.findViewById(R.id.btn_decline_ride);
		
		if (!mPassenger.isApproved()) {
		
		btnAccept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View v) {
				
				ImageButton btnAccept = (ImageButton) ((View)v.getParent()).findViewById(R.id.btn_accept_ride);
				ImageButton btnReject = (ImageButton) ((View)v.getParent()).findViewById(R.id.btn_decline_ride);
				
				btnAccept.setEnabled(false);
				btnReject.setEnabled(false);
				
				mPassenger.setIsApproved(true);
				mPassenger.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException e) {
					
					if (e == null) {
						
						AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
						String notificationMessage = 
								authUser.getName() + " has accepted your request.";	
						generateNotification(mPassenger, AppConstants.NOTIFICATION_TYPE_RIDE_ACCEPTED, notificationMessage);
						
						// Update listview adapter
						mPassenger.setIsApproved(true);
						mAdapter.notifyDataSetChanged();
						
					} else {
						
						// An error occurred
						Toast.makeText(getContext(), "This passenger cannot be accepted at this time. Please try agian later [Error: " + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
					
					}
					
				}
				
			});
				
			}
		});
		
		btnReject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mRide.removePassenger(mPassenger);
				mRide.saveInBackground();
				
				mPassenger.deleteInBackground(new DeleteCallback() {
				
				@Override
				public void done(ParseException e) {
					
					if (e == null) {
					
						AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
						String notificationMessage = 
								authUser.getName() + " has rejected your request.";	
						generateNotification(mPassenger, AppConstants.NOTIFICATION_TYPE_RIDE_REJECTED, notificationMessage);
					
						// Update listview adapter
						mAdapter.remove(mPassenger);
						mAdapter.notifyDataSetChanged();
						
					} else {
						
						// An error occurred
						Toast.makeText(getContext(), "This passenger cannot be rejected at this time. Please try agian later [Error: " + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
					
					}
					
				}
				
			});
				
			}
		});
		
		} else {
		
			// Passenger has been approved
			
			btnAccept.setVisibility(Button.GONE);
			btnReject.setVisibility(Button.GONE);
			
		}
		
		return mView;
	}

	/**
	 * Utility Method to generate a notification
	 * 
	 * @param passenger
	 * @param notificationKey
	 * @param notificationMessage
	 */
	private void generateNotification(final Passenger passenger, int notificationKey, String notificationMessage) {
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		// Create notification
		Notification notification = new Notification();
		notification.setFromUserId(authUser.getFacebookId());
		notification.setToUserId(passenger.getUserId());
		notification.setHasBeenRead(false);
		notification.setNotificationMessageForRecipient(notificationMessage);
		notification.setNotificationType(notificationKey);
		notification.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				
				//TODO: Implement feedback mecanism for the user
//				Toast.makeText(getContext(), "We just sent a notification to " //+ driver.getUserId() 
//						+ "" + passenger.getName() + "", Toast.LENGTH_SHORT).show();
				
			}
		});
		
//		// Send push notification to recipient
//		ParsePush push = new ParsePush();
//		push.setChannel("user-" + passenger.getUserId());
//		push.setMessage(notificationMessage);
//		push.sendInBackground();
	}
	
}
