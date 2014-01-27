package com.yahoo.mobileacademy.carpool.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yahoo.mobileacademy.carpool.fragments.LoginFragment;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;

/**
 * Activity that manage authentication to the application
 * using Facebook login
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class LoginActivity extends FragmentActivity {

	private LoginFragment loginFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.activity_login);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        loginFragment = new LoginFragment();
	        getSupportFragmentManager().beginTransaction().add(android.R.id.content, loginFragment).commit();
	    } else {
	        // Or set the fragment from restored state info
	    	loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
	    }
		
	}
	
	/**
	 * User clicks on the Skip Login button
	 * @param v
	 */
	public void onSkipLoginAction(View v) {
		
    	Intent i = new Intent(this, RoleSelectionActivity.class);
    	startActivity(i);
    	
	}

}
