package com.yahoo.mobileacademy.carpool.activities;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.yahoo.mobileacademy.carpool.R;

/**
 * Activity that manage authentication to the application
 * using Facebook login
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class LoginActivity extends FragmentActivity {

	//private LoginFragment loginFragment;
	private Dialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		if (savedInstanceState == null) {
//	        // Add the fragment on initial activity setup
//	        loginFragment = new LoginFragment();
//	        getSupportFragmentManager().beginTransaction().add(android.R.id.content, loginFragment).commit();
//	    } else {
//	        // Or set the fragment from restored state info
//	    	loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
//	    }
		
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			showRoleSelectionActivity();
		}
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	
//	/**
//	 * User clicks on the Skip Login button
//	 * @param v
//	 */
//	public void onSkipLoginAction(View v) {
//		
//    	Intent i = new Intent(this, RoleSelectionActivity.class);
//    	startActivity(i);
//    	
//	}
	
	public void onLoginButtonClicked(View v) {
		progressDialog = ProgressDialog.show(
				this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
				"user_relationships", "user_birthday", "user_location");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				progressDialog.dismiss();
				if (user == null) {
					Log.d("debug",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("debug",
							"User signed up and logged in through Facebook!");
					showRoleSelectionActivity();
				} else {
					Log.d("debug",
							"User logged in through Facebook!");
					showRoleSelectionActivity();
				}
			}
		});
	}
	
	private void showRoleSelectionActivity() {
		
		makeMeRequest();
		
		Intent i = new Intent(getBaseContext(), RoleSelectionActivity.class);
    	startActivity(i);
		
	}
	
	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							// Create a JSON object to hold the profile info
							JSONObject userProfile = new JSONObject();
							try {
								// Populate the JSON object
								userProfile.put("facebookId", user.getId());
								userProfile.put("name", user.getName());
								userProfile.put("first_name", user.getFirstName());
								userProfile.put("last_name", user.getLastName());
								if (user.getLocation().getProperty("name") != null) {
									userProfile.put("location", (String) user
											.getLocation().getProperty("name"));
								}
								if (user.getProperty("gender") != null) {
									userProfile.put("gender",
											(String) user.getProperty("gender"));
								}

								// Save the user profile info in a user property
								ParseUser currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();

							} catch (JSONException e) {
								Log.d("debug",
										"Error parsing returned user data.");
							}

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d("debug",
										"The facebook session was invalidated.");
								//finish();
							} else {
								Log.d("debug",
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}

}
