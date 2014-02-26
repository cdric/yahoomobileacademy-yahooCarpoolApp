package com.yahoo.mobileacademy.carpool.activities;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractSlidingMenuActivity;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment.TimePickedListener;
import com.yahoo.mobileacademy.carpool.fragments.driver.DriverRideDetailsFragment;
import com.yahoo.mobileacademy.carpool.fragments.driver.DriverRideDetailsFragment.OnDriverRideDetailsFragmentListener;
import com.yahoo.mobileacademy.carpool.fragments.driver.DriverRideFragment;
import com.yahoo.mobileacademy.carpool.listeners.AsyncFragmentRefreshListener;
import com.yahoo.mobileacademy.carpool.listeners.FragmentTabListener;
import com.yahoo.mobileacademy.carpool.models.Ride;



public class DriverActivity extends AbstractSlidingMenuActivity
							implements TimePickedListener, 
										OnDriverRideDetailsFragmentListener,
										AsyncFragmentRefreshListener {
	
	private DriverRideDetailsFragment fragmentDriverRideDetails;
	//private GenericNotificationsFragment fragmentNotifications;
	
	private Tab tabRide, tabNotifications;
	private String tagRide, tagNotifications;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role);
		
		setUpActivity();
		setUpSlidingMenu(savedInstanceState);

	}
		
	private void setUpActivity() {
	
		//AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		// Update action bar title & icon
		updateActionBarTitle(R.string.driver, true);
		
		// Setup action bar properties
		ActionBar actionBar = getActionBar(); 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		// Define action bar tabs
		tagRide = getResources().getString(R.string.tab_driver_ridedetails);
		tabRide = actionBar.newTab().setText(R.string.tab_driver_ridedetails).setTabListener(
		new FragmentTabListener(this, tagRide, DriverRideFragment.class)).setIcon(R.drawable.ic_action_driver).setTag(tagRide);
		
		tagNotifications = getResources().getString(R.string.tab_driver_notifications);
		tabNotifications = actionBar.newTab().setText(R.string.tab_driver_notifications).setTabListener(
		new FragmentTabListener(this, tagNotifications, GenericNotificationsFragment.class)).setIcon(R.drawable.ic_action_notification).setTag(tagNotifications);
		
		setUpActionBar();
		
	}
	
	/**
	* Define the list of active tabs based on
	* the status of the ride
	*/
	private void setUpActionBar() {
	
		android.app.ActionBar actionBar = getActionBar(); 
		actionBar.addTab(tabRide);
		actionBar.addTab(tabNotifications);
		actionBar.selectTab(tabRide);
		
	}
	
	
	@Override
	public void onTimePicked(Calendar time) {
		// display the selected time in the TextView
		EditText etDriverRideStart = (EditText) fragmentDriverRideDetails.getView().findViewById(R.id.et_driver_time_ride_start);
		etDriverRideStart.setText(DateFormat.format(AppConstants.DRIVER_RIDE_DATE_FORMAT, time));
	}
	
	// BUTTON EVENTS
	
	public void onSaveRideAction(View v) {
		
		fragmentDriverRideDetails.onSaveRideAction(v);
	
	}
	
	
	public void onDeleteRideAction(View v) {
		fragmentDriverRideDetails.onDeleteRideAction(v);
	}
	
	
	// METHODS FROM INTERFACE OnDriverRideDetailsFragmentListene
	
	@Override
	public void onSaveRideEvent(Ride r) {
	
		r.saveInBackground(new SaveCallback() {
		
		@Override
		public void done(ParseException e) {
			//Toast.makeText(getBaseContext(), "Your ride have been save successfully!", Toast.LENGTH_SHORT).show();
		}
		});
	
	}
	
	@Override
	public void onDeleteRideEvent(ParseObject ride) {
		ride.deleteInBackground();
	}
	
	@Override
	public void onDriverRideDetailsFragmentReady(DriverRideDetailsFragment f) {
		fragmentDriverRideDetails = f;
	}
	
	// METHODS FROM INTERFACE OnPassengerSearchRideFragmentListener

	@Override
	public void asyncContentRefreshTaskStarted(String refreshStatus) {
		setProgressBarDialogTitle(refreshStatus);
		showProgressBarDialog();
	}

	@Override
	public void asyncContentRefreshTaskCompleted() {
		hideProgressBarDialog();
	}

}
