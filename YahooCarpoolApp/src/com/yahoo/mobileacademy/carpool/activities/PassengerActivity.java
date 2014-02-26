package com.yahoo.mobileacademy.carpool.activities;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractSlidingMenuActivity;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment.TimePickedListener;
import com.yahoo.mobileacademy.carpool.fragments.passenger.PassengerSearchRideFragment;
import com.yahoo.mobileacademy.carpool.listeners.AsyncFragmentRefreshListener;
import com.yahoo.mobileacademy.carpool.listeners.FragmentTabListener;

public class PassengerActivity extends AbstractSlidingMenuActivity
							   implements TimePickedListener, 
							   AsyncFragmentRefreshListener {

	private PassengerSearchRideFragment fragmentPassengerSeachRideFragment;
	//private GenericNotificationsFragment fragmentNotifications;
	
	private Tab tabSearch, tabNotifications;
	private String tagSearch, tagNotifications;

	
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
    	updateActionBarTitle(R.string.passenger, true);
    	
		// Setup action bar properties
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		// Define action bar tabs
		tagSearch = getResources().getString(R.string.tab_passenger_search);
		tabSearch = actionBar.newTab().setText(R.string.tab_passenger_search).setTabListener(
				new FragmentTabListener(this, tagSearch, PassengerSearchRideFragment.class)).setIcon(R.drawable.ic_action_search).setTag(tagSearch);

		tagNotifications = getResources().getString(R.string.tab_passenger_notifications);
		tabNotifications = actionBar.newTab().setText(R.string.tab_passenger_notifications).setTabListener(
				new FragmentTabListener(this, tagNotifications, GenericNotificationsFragment.class)).setIcon(R.drawable.ic_action_notification).setTag(tagNotifications);
		
		actionBar.addTab(tabSearch);
		actionBar.addTab(tabNotifications);
		
		actionBar.selectTab(tabSearch);
	}

	
	@Override
	public void onTimePicked(Calendar time) {
	    // display the selected time in the TextView 
		EditText etDriverRideStart = (EditText) findViewById(R.id.et_time_ride_start);
		etDriverRideStart.setText(DateFormat.format(AppConstants.DRIVER_RIDE_DATE_FORMAT, time));
	}
	
	// BUTTON ACTIONS
	
	public void onSearchRideAction(View v) {
		
		setUpProgressBarDialog("Searching...");
		showProgressBarDialog();
		
		// Load fragments
		if (fragmentPassengerSeachRideFragment == null) {
			fragmentPassengerSeachRideFragment = (PassengerSearchRideFragment) 
				getSupportFragmentManager().findFragmentByTag(tagSearch);
		}
		
		fragmentPassengerSeachRideFragment.onSearchRideAction(v);
		
	}
		
	// METHODS FROM INTERFACE AsyncFragmentRefreshListener

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
