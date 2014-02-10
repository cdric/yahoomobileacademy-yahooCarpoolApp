package com.yahoo.mobileacademy.carpool.apps;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.PushService;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.DriverActivity;

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
		
		// Initialize ActiveAndroid
		ActiveAndroid.initialize(this);

		// Create global configuration and initialize ImageLoader with this
		// configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);
		
		// Initialize Parse
		Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
		ParseFacebookUtils.initialize(getResources().getString(R.string.app_id));
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();		
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