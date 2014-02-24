package com.yahoo.mobileacademy.carpool.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractRoleActivity;
import com.yahoo.mobileacademy.carpool.adapters.NavDrawerListAdapter;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.fragments.DriverPassengerListFragment.OnDriverPassengerListFragmentListener;
import com.yahoo.mobileacademy.carpool.fragments.DriverRideDetailsFragment;
import com.yahoo.mobileacademy.carpool.fragments.DriverRideDetailsFragment.OnDriverRideDetailsFragmentListener;
import com.yahoo.mobileacademy.carpool.fragments.DriverRideFragment;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.GenericNotificationsFragment.OnGenericNotificationsFragment;
import com.yahoo.mobileacademy.carpool.fragments.PassengerSearchRideFragment.OnPassengerSearchRideFragmentListener;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment.TimePickedListener;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.listeners.FragmentTabListener;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.NavDrawerItem;
import com.yahoo.mobileacademy.carpool.models.Ride;



public class DriverActivityNew extends AbstractRoleActivity 
							implements TimePickedListener, 
										OnDriverRideDetailsFragmentListener,
										OnDriverPassengerListFragmentListener,
										OnGenericNotificationsFragment{
	
	private DriverRideDetailsFragment fragmentDriverRideDetails;
	private GenericNotificationsFragment fragmentNotifications;
	
	private Tab tabRide, tabNotifications;
	private String tagRide, tagNotifications;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_new);
		
		setUpActivity();
		setUpProgressBarDialog("Loading...");
		
		setUpDrawer();
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.driver, menu);
		return true;
	}
	
	
	
	private void setUpActivity() {
	
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		// Update action bar title & icon
		updateActionBarTitle(R.string.driver);
		updateActionBarIconWithAuthUserFacebookProfileIcon(authUser.getFacebookId());
		
		// Setup action bar properties
		ActionBar actionBar = getActionBar(); 
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		
		// Define action bar tabs
		tagRide = getResources().getString(R.string.tab_driver_ridedetails);
		tabRide = actionBar.newTab().setText(R.string.tab_driver_ridedetails).setTabListener(
		new FragmentTabListener(this, tagRide, DriverRideFragment.class)).setIcon(R.drawable.ic_action_driver).setTag(tagRide);
		
		tagNotifications = getResources().getString(R.string.tab_driver_notifications);
		tabNotifications = actionBar.newTab().setText(R.string.tab_driver_notifications).setTabListener(
		new FragmentTabListener(this, tagNotifications, GenericNotificationsFragment.class)).setIcon(R.drawable.ic_action_notifications).setTag(tagNotifications);
		
		setUpActionBar();
	
	}
	
	/**
	* Define the list of active tabs based on
	* the status of the ride
	*/
	private void setUpActionBar() {
	
		ActionBar actionBar = getActionBar(); 
		
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
		//setupFragmentDriverRideDetails();
		fragmentDriverRideDetails.onDeleteRideAction(v);
		//setUpActionBar(false);
	}
	
	
	// METHODS FROM INTERFACE OnDriverRideDetailsFragmentListene
	
	@Override
	public void onSaveRideEvent(Ride r) {
	
		r.saveInBackground(new SaveCallback() {
		
		@Override
		public void done(ParseException e) {
			Toast.makeText(getBaseContext(), "Your ride have been save successfully!", Toast.LENGTH_SHORT).show();
			//setUpActionBar(true);
		}
		});
	
	}
	
	@Override
	public void onDeleteRideEvent(ParseObject ride) {
		ride.deleteInBackground();
	}
	
	//@Override
	//public void rideIsDefined(ParseObject ride) {
	////poRide = ride;
	////setUpActionBar(true); 
	//}
	
	@Override
	public void onDriverRideDetailsFragmentReady(DriverRideDetailsFragment f) {
		fragmentDriverRideDetails = f;
	}
	
	// METHODS FROM INTERFACE OnGenericNotificationsFragmentListener
	
	@Override
	public void onGenericNotificationsFragmentReady(GenericNotificationsFragment f) {
		fragmentNotifications = f;	
	}
	
	// METHODS FROM INTERFACE OnPassengerSearchRideFragmentListener

	@Override
	public void asyncTaskStarted() {
		showProgressBarDialog();
	}
	
	@Override
	public void asynTaskCompleted() {
		hideProgressBarDialog();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
 
    private void setUpDrawer() {
        
    	mTitle = mDrawerTitle = getTitle();
 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility 
        ){
                
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
    }
  
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_profileSelection).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new DriverRideFragment();
			break;
//		case 1:
//			fragment = new FindPeopleFragment();
//			break;
//		case 2:
//			fragment = new PhotosFragment();
//			break;
//		case 3:
//			fragment = new CommunityFragment();
//			break;
//		case 4:
//			fragment = new PagesFragment();
//			break;
//		case 5:
//			fragment = new WhatsHotFragment();
//			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

}
