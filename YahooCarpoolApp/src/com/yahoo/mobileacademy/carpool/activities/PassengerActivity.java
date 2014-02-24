package com.yahoo.mobileacademy.carpool.activities;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractRoleActivity;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment.OnGenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.PassengerSearchRideFragment;
import com.yahoo.mobileacademy.carpool.fragments.PassengerSearchRideFragment.OnPassengerSearchRideFragmentListener;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment.TimePickedListener;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.listeners.FragmentTabListener;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;

public class PassengerActivity extends AbstractRoleActivity
							   implements TimePickedListener, 
							   OnPassengerSearchRideFragmentListener,
							   OnGenericNotificationsFragment{

	private PassengerSearchRideFragment fragmentPassengerSeachRideFragment;
	private GenericNotificationsFragment fragmentNotifications;
	
	private Tab tabSearch, tabNotifications;
	private String tagSearch, tagNotifications;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        setUpActivity();
        setUpProgressBarDialog("Searching...");
    }

	private void setUpActivity() {
    	
    	AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
    	
    	// Update action bar title & icon
    	updateActionBarTitle(R.string.passenger);
    	updateActionBarIconWithAuthUserFacebookProfileIcon(authUser.getFacebookId());
	 
		// Setup action bar properties
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		// Define action bar tabs
		tagSearch = getResources().getString(R.string.tab_passenger_search);
		tabSearch = actionBar.newTab().setText(R.string.tab_passenger_search).setTabListener(
				new FragmentTabListener(this, tagSearch, PassengerSearchRideFragment.class)).setIcon(R.drawable.ic_action_search).setTag(tagSearch);

		tagNotifications = getResources().getString(R.string.tab_passenger_notifications);
		tabNotifications = actionBar.newTab().setText(R.string.tab_passenger_notifications).setTabListener(
				new FragmentTabListener(this, tagNotifications, GenericNotificationsFragment.class)).setIcon(R.drawable.ic_action_notifications).setTag(tagNotifications);
		
		actionBar.addTab(tabSearch);
		actionBar.addTab(tabNotifications);
		
		actionBar.selectTab(tabSearch);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.passenger, menu);
		getActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }
	
	@Override
	public void onTimePicked(Calendar time) {
	    // display the selected time in the TextView
		EditText etDriverRideStart = (EditText) findViewById(R.id.et_time_ride_start);
		etDriverRideStart.setText(DateFormat.format(AppConstants.DRIVER_RIDE_DATE_FORMAT, time));
	}
	
	// BUTTON ACTIONS
	
	public void onSearchRideAction(View v) {
		
		showProgressBarDialog();
		
		// Load fragments
		if (fragmentPassengerSeachRideFragment == null) {
			fragmentPassengerSeachRideFragment = (PassengerSearchRideFragment) 
				getSupportFragmentManager().findFragmentByTag(tagSearch);
		}
		
		fragmentPassengerSeachRideFragment.onSearchRideAction(v);
		
	}
	
	// METHODS FROM INTERFACE OnGenericNotificationsFragmentListener

	@Override
	public void onGenericNotificationsFragmentReady(
			GenericNotificationsFragment f) {
		fragmentNotifications = f;
		
	}
	
	// METHODS FROM INTERFACE OnPassengerSearchRideFragmentListener

	@Override
	public void asynTaskCompleted() {
		hideProgressBarDialog();
	}
    
    
    
}
