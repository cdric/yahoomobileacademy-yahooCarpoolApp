package com.yahoo.mobileacademy.carpool.apps;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.DriverActivity;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Notification;
import com.yahoo.mobileacademy.carpool.models.Passenger;
import com.yahoo.mobileacademy.carpool.models.Ride;
import com.yahoo.mobileacademy.carpool.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 *     
 */
public class CarpoolApp extends com.activeandroid.app.Application {
	
	private static Context context;
	
	private static final String PARSE_APPLICATION_ID = "WWooSiNqgiHAEfcR4BY2HqSOuvfr3pIN5zwV88Nv";
	private static final String PARSE_CLIENT_KEY = "U0oXEeNOgjZyQvrGnDHCZggA3r63ZNa3r6JjEmxL";
	
	@Override
	public void onCreate() {
		super.onCreate(); 
		CarpoolApp.context = this;
		
		// -- Initialize ActiveAndroid --
		ActiveAndroid.initialize(this);

		// Create global configuration and initialize ImageLoader with this configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);
		
		// -- Initialize Parse --
		// Register your parse models
	    ParseObject.registerSubclass(User.class);
	    ParseObject.registerSubclass(Ride.class);
	    ParseObject.registerSubclass(Passenger.class);
	    ParseObject.registerSubclass(Driver.class); 
	    ParseObject.registerSubclass(Notification.class); 
	    // Init
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(getResources().getString(R.string.app_id));
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();	
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true); 
		// Setup push notification 
		PushService.setDefaultPushCallback(this, DriverActivity.class); 
		
	}
	
	@Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
	

}