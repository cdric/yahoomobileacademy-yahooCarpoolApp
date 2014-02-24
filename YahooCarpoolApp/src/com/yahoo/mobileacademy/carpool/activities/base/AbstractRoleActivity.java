package com.yahoo.mobileacademy.carpool.activities.base;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
	
	/**
	 * Update the action bar icon with the image of the facebook user
	 * 
	 * @param facebookId the FacebookId for this user
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	protected void updateActionBarIconWithAuthUserFacebookProfileIcon(String facebookId) {
		// Update Action Bar to replace icon with user profile icon
		
		Drawable myFacebookDrawable;
		try {
			Uri uri = Uri.parse(UtilityClass.getDisplayImageURLForFacebookId(facebookId));
		    InputStream inputStream = getContentResolver().openInputStream(uri);
		    myFacebookDrawable = Drawable.createFromStream(inputStream, uri.toString() );
		    getActionBar().setIcon(myFacebookDrawable);
		} catch (FileNotFoundException e) {
			// Don't update the icon from the actino bar
		}
	}
	
    /**
     * Prepare for a progress bar Dialog
     */
    protected void setUpProgressBarDialog(String message) {
    	
		pbDialog = new ProgressDialog(this);
		pbDialog.setCancelable(true);
		pbDialog.setMessage(message);
		pbDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//pbDialog.setProgress(0);
		//pbDialog.setMax(100);
		//pbDialog.setTitle("Please wait...");
		
	}
    
	protected ProgressDialog pbDialog;
    
    /**
     * Show the progress bar dialog
     */
    protected void showProgressBarDialog() {
    	pbDialog.show();
    }
    
    /**
     * Hide the progress bar dialog
     */
    protected void hideProgressBarDialog() {
    	pbDialog.hide();
    }

}
