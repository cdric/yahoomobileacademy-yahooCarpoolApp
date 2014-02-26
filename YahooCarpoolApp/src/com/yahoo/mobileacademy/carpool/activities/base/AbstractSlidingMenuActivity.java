package com.yahoo.mobileacademy.carpool.activities.base;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.fragments.nav.NavDrawerListFragment;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;

public abstract class AbstractSlidingMenuActivity extends SlidingFragmentActivity {
	
	private NavDrawerListFragment mFrag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// in onCreate
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		getActionBar().setIcon(R.drawable.ic_drawer);
		
		// Workarround to remove the home icon
		// This is to address https://github.com/JakeWharton/ActionBarSherlock/issues/327
		View homeIcon = findViewById(android.R.id.home);
		//((View) homeIcon.getParent()).setVisibility(View.GONE);
		
		getActionBar().setCustomView(R.layout.actionbar_carpool);
	}

	/**
	 *  Update the action bar with a prefix and the name of the currently
	 *  authenticated user
	 *  
	 *  @param prefix the string ref for the title in the action bar
	 */
	protected void updateActionBarTitle(int prefixId, boolean includeUserName) {
		
		// Update Action Bar to include user name
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		
		String title;
		if (includeUserName) {
		   title = getResources().getString(prefixId) + ": " + user.getName();
		} else {
		   title = getResources().getString(prefixId);
		}
		
		getActionBar().setIcon(null);
		
		TextView tv = (TextView) findViewById(R.id.tv_actionbar_title);
		tv.setText(title);
		
		updateActionBarIconWithAuthUserFacebookProfileIcon(user.getFacebookId());
	}
	
	/**
	 * Update the action bar icon with the image of the facebook user
	 * 
	 * @param facebookId the FacebookId for this user
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void updateActionBarIconWithAuthUserFacebookProfileIcon(String facebookId) {
				    
	    ImageView iv = (ImageView) findViewById(R.id.iv_actionbar_profileIcon);
	    ImageLoader.getInstance().displayImage(
	 		   UtilityClass.getDisplayImageURLForFacebookId(facebookId), iv);
		    
	}
	
	
    
    public void switchContent(final Fragment fragment) {
    	
    	getSlidingMenu().showContent(true);
    	
		// Thread to wait on purpose
		Thread timer = new Thread(){
	        public void run(){
	            try{
	                int logoTimer = 0;
	                while (logoTimer<300){
	                    sleep(100);
	                    logoTimer+=100;
	                }
	                
	                //mContent = fragment;
	        		getSupportFragmentManager()
	        		.beginTransaction()
	        		.replace(R.id.frame_container, fragment)
	        		.commit();
	    			
		        } catch (InterruptedException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } finally { }
	        }
	    };
	    timer.start();
		
	}
    
	public void setUpSlidingMenu(Bundle savedInstanceState) {
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new NavDrawerListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (NavDrawerListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
	
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	
		// Remove "<" icon
		// getActionBar().setDisplayHomeAsUpEnabled(false);
				
		// configure the SlidingMenu
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_sliding_menu);
        //menu.setSelectorDrawable(R.drawable.ic_drawer);
         
	}

	// ----------------------------------
	// PROGRESSBAR DIALOG RELATED METHODS
	// ----------------------------------
	
    /**
     * Prepare for a progress bar Dialog
     */
    protected void setUpProgressBarDialog(String message) {
    	
    	if (pbDialog == null) {
    		pbDialog = new ProgressDialog(this);
    	}
    	
		pbDialog.setCancelable(true);
		pbDialog.setMessage(message);
		pbDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
	}
    
	protected ProgressDialog pbDialog;
    
    /**
     * Show the progress bar dialog
     */
    public void showProgressBarDialog() {
    	pbDialog.show();
    }
    
    /**
     * Hide the progress bar dialog
     */
    public void hideProgressBarDialog() {
    	pbDialog.hide();
    }
	    
	/**
	 * Update the title of the ProgressBar Dialog
	 * @param title
	 */
	public void setProgressBarDialogTitle(String title) {
		setUpProgressBarDialog(title);
	}

}
