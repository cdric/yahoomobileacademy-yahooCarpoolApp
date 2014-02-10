package com.yahoo.mobileacademy.carpool.activities.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;

public abstract class AbstractRoleActivity extends FragmentActivity {
	
	/**
	 * Method invoked when user click on the "profile" icon from the action bar
	 * @param mi the MenuItem
	 */
	public void onRoleSelectionMenu(MenuItem mi) {
		
		Intent i = getIntent();
		setResult(RESULT_OK, i);
		finish();

	}
	
	/**
	 *  Update the action bar with a prefix and the name of the currently
	 *  authenticated user
	 *  
	 *  @param prefix the string ref for the title in the action bar
	 */
	protected void updateActionBarTitle(int prefixId) {
		
		// Update Action Bar to include user name
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		getActionBar().setTitle(getResources().getString(prefixId) + ": " + user.getName());
		
	}

}
