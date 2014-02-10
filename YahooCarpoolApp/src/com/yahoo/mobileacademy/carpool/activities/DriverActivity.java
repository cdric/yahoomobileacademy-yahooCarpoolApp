package com.yahoo.mobileacademy.carpool.activities;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractRoleActivity;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.fragments.DriverGuestNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.DriverPassengerListFragment;
import com.yahoo.mobileacademy.carpool.fragments.DriverRideDetailsFragment;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment.TimePickedListener;
import com.yahoo.mobileacademy.carpool.listeners.FragmentTabListener;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class DriverActivity extends AbstractRoleActivity 
				implements TimePickedListener, 
						DriverRideDetailsFragment.OnDriverRideDetailsFragmentListene {
	
	
	DriverRideDetailsFragment fragmentDriverRideDetails;
	ParseObject poRide;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        setUpActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver, menu);
        return true;
    }
    
    private Tab tabRide, tabNotifications, tabPassengersList;
	private String tagRide, tagNotifications, tagPassengersList;
	
	private void setUpActivity() {
		
		// Update action bar title
		updateActionBarTitle(R.string.driver);
	 
		// Setup action bar properties
		ActionBar actionBar = getActionBar(); 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		// Define action bar tabs
		tagRide = getResources().getString(R.string.tab_driver_ridedetails);
		tabRide = actionBar.newTab().setText(R.string.tab_driver_ridedetails).setTabListener(
				new FragmentTabListener(this, tagRide, DriverRideDetailsFragment.class)).setIcon(R.drawable.ic_action_driver).setTag(tagRide);

		tagNotifications = getResources().getString(R.string.tab_driver_notifications);
		tabNotifications = actionBar.newTab().setText(R.string.tab_driver_notifications).setTabListener(
				new FragmentTabListener(this, tagNotifications, DriverGuestNotificationsFragment.class)).setIcon(R.drawable.ic_action_notifications).setTag(tagNotifications);
		
		tagPassengersList = getResources().getString(R.string.tab_driver_passengers);
		tabPassengersList = actionBar.newTab().setText(R.string.tab_driver_passengers).setTabListener(
				new FragmentTabListener(this, tagRide, DriverPassengerListFragment.class)).setIcon(R.drawable.ic_action_passenger).setTag(tagPassengersList);
		
		setUpActionBar(false);
	}
	
	/**
	 * Define the list of active tabs based on
	 * the status of the ride
	 * @param rideDefined is the ride defined?
	 */
	private void setUpActionBar(boolean rideDefined) {
		
		ActionBar actionBar = getActionBar(); 
		//actionBar.removeAllTabs();
		
		if (rideDefined) {
			actionBar.addTab(tabPassengersList, 0);
			actionBar.addTab(tabNotifications, 2);
			actionBar.selectTab(tabRide);
		} else { 
			if (actionBar.getNavigationItemCount() >1) {
				actionBar.removeTab(tabPassengersList);	
				actionBar.removeTab(tabNotifications);	
			} else {
				actionBar.addTab(tabRide);
			}
		}
	}
	
	
	@Override
	public void onTimePicked(Calendar time) {
	    // display the selected time in the TextView
		EditText etDriverRideStart = (EditText) findViewById(R.id.et_driver_time_ride_start);
		etDriverRideStart.setText(DateFormat.format(AppConstants.DRIVER_RIDE_DATE_FORMAT, time));
	}
	
	// BUTTON EVENTS
	
	public void onSaveRideAction(View v) {
		
		// Load fragments
		if (fragmentDriverRideDetails == null) {
			fragmentDriverRideDetails = (DriverRideDetailsFragment) 
				getSupportFragmentManager().findFragmentByTag(tagRide);
		}
		
		fragmentDriverRideDetails.onSaveRideAction(v);
		
	}
	
	
	public void onDeleteRideAction(View v) {
		setupFragmentDriverRideDetails();
		fragmentDriverRideDetails.onDeleteRideAction(v);
		setUpActionBar(false);
	}

	
	// METHODS FROM INTERFACE OnDriverRideDetailsFragmentListene
	
	@Override
	public void onSaveRideEvent(Ride r) {

		ParseObject driver = r.getDriver().toParseObject();
		ParseObject ride = r.toParseObject();
		ride.put("driverRef", driver);		
		ride.saveInBackground(new SaveCallback() {
			
			@Override
			public void done(ParseException e) {
				Toast.makeText(getBaseContext(), "Your ride have been save successfully!", Toast.LENGTH_SHORT).show();
				setUpActionBar(true);
			}
		});
		
	}
	
	@Override
	public void onDeleteRideEvent(ParseObject ride) {
		ride.deleteInBackground();
	}

	@Override
	public void rideIsDefined(ParseObject ride) {
		poRide = ride;
		setUpActionBar(true); 
	}
	
	/**
	 * Initialize the fragmentDriverRideDetails variable
	 * with the appropriate fragment from this activity
	 */
	private void setupFragmentDriverRideDetails() {
		// Load fragment
		if (fragmentDriverRideDetails == null) {
			fragmentDriverRideDetails = (DriverRideDetailsFragment) 
				getSupportFragmentManager().findFragmentByTag(tagRide);
		}
	}
    
}
