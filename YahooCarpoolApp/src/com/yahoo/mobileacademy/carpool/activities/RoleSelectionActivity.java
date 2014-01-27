package com.yahoo.mobileacademy.carpool.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.yahoo.mobileacademy.carpool.R;

public class RoleSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selection);
		
		setUpView();
	}

	private void setUpView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.role_selection, menu);
		return true;
	}
	
	/**
	 * User clicks on the driver icon
	 * @param v the button
	 */
	public void onDriverAction(View v) {
		
		//Toast.makeText(getBaseContext(), "I'm a driver!", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, DriverActivity.class);
		startActivity(i);
		
	}
	
	/**
	 * User clicks on the driver icon
	 * @param v the button
	 */
	public void onPassengerAction(View v) {
	
		// Toast.makeText(getBaseContext(), "I'm a passenger!", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, PassengerActivity.class);
		startActivity(i);
		
	}

}
