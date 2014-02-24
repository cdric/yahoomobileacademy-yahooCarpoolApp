package com.yahoo.mobileacademy.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Session;
import com.parse.ParseUser;
import com.yahoo.mobileacademy.carpool.R;



public class RoleSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selection);
		
		setUpView();
		
		//ActionBar actionBar = getActionBar();
	    //actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void setUpView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.role_selection, menu);
		getActionBar().setDisplayShowHomeEnabled(false);
		return true;
	}
	
	/**
	 * User clicks on the driver icon
	 * @param v the button
	 */
	public void onDriverAction(View v) {
		//Intent i = new Intent(this, DriverActivity.class);
		Intent i = new Intent(this, DriverActivityNew.class);
		startActivity(i); 
		//overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);              
	}
	
	/**
	 * User clicks on the driver icon
	 * @param v the button
	 */
	public void onPassengerAction(View v) {
		Intent i = new Intent(this, PassengerActivity.class);
		startActivity(i);
	}
	
	// --------------------------
	// ACTION BAR RELATED ACTIONS
	// --------------------------
	
	/**
	 * User clicks on the driver icon
	 * @param v the button
	 */
	public void onSignoutAction(MenuItem mi) {
	
		// Log the user out
		ParseUser.logOut();
				
		Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
            Log.d("debug",
					"Closing Facebook session");
        }
        
        finish();
		
	}

}
