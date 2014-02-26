package com.yahoo.mobileacademy.carpool.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.parse.ParseInstallation;
import com.parse.PushService;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractBaseFragmentActivity;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;

public class RoleSelectionActivity extends AbstractBaseFragmentActivity implements AnimationListener {

	private Animation animation1, animation2;
    private Animation animation3, animation4;
    private boolean isBackOfCardShowingBtnDriver = true;
    private boolean isBackOfCardShowingBtnPassenger = true;
    
    ImageView ivDriver, ivPassenger;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selection);
		
		setUpActivity();
		
//		// Associate the device with a user
//		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
//		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//		installation.put("userFacebookId", authUser.getFacebookId());
//		installation.saveInBackground();
//		
//		// Subscribe to the user's channel
//		PushService.subscribe(getApplicationContext(), "user" + authUser.getFacebookId(), LoginActivity.class);
		
	}

	private void setUpActivity() {
		
		updateActionBarTitle(R.string.action_profile, false);
		
		ivDriver = (ImageView) findViewById(R.id.iv_driver_icon);
		ivPassenger = (ImageView) findViewById(R.id.iv_passenger_icon);
		
		animation1 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation1.setAnimationListener(this);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation2.setAnimationListener(this);
        
        animation3 = AnimationUtils.loadAnimation(this, R.anim.to_middle);
        animation3.setAnimationListener(this);
        animation4 = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        animation4.setAnimationListener(this);
        
        // Setup animation on driver button
        ivDriver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//v.setEnabled(false);
				ivDriver.clearAnimation();
				ivDriver.setAnimation(animation1);
				ivDriver.startAnimation(animation1);
			}
		});
        
        // Setup animation on passenger button
        ivPassenger.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//v.setEnabled(false);
				ivPassenger.clearAnimation();
				ivPassenger.setAnimation(animation3);
				ivPassenger.startAnimation(animation3);
			}
		});
        
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
		Intent i = new Intent(this, DriverActivity.class);
		startActivity(i); 
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
	
		UtilityClass.signOutAuthenticatedUser(this);
		
	}
	
	// -----------------------------
	// ANIMATION BAR RELATED ACTIONS
	// -----------------------------

    @Override
    public void onAnimationEnd(Animation animation) {
    	
		if (animation == animation1) {
			if (isBackOfCardShowingBtnDriver) {
				ivDriver
						.setImageResource(R.drawable.driver);
			} else {
				ivDriver
						.setImageResource(R.drawable.driver);
			}
			ivDriver.clearAnimation();
			ivDriver
					.setAnimation(animation2);
			ivDriver
					.startAnimation(animation2);
		} 
		
		if (animation == animation2) {
			isBackOfCardShowingBtnDriver = !isBackOfCardShowingBtnDriver;
			//findViewById(R.id.iv_passenger_icon).setEnabled(true);
			
			Intent i = new Intent(this, DriverActivity.class);
			startActivity(i); 
		}
		
		if (animation == animation3) {
			if (isBackOfCardShowingBtnPassenger) {
				ivPassenger
						.setImageResource(R.drawable.passenger_4);
			} else {
				ivPassenger
						.setImageResource(R.drawable.passenger_4);
			}
			ivPassenger.clearAnimation();
			ivPassenger
					.setAnimation(animation4);
			ivPassenger
					.startAnimation(animation4);
		} 
		
		if (animation == animation4) {
			isBackOfCardShowingBtnPassenger = !isBackOfCardShowingBtnPassenger;
			//findViewById(R.id.iv_passenger_icon).setEnabled(true);
			
			Intent i = new Intent(this, PassengerActivity.class);
			startActivity(i);
		}
		
    }
    @Override
    public void onAnimationRepeat(Animation animation) {
           // TODO Auto-generated method stub
    }
    @Override
    public void onAnimationStart(Animation animation) {
    // TODO Auto-generated method stub
    }

}
