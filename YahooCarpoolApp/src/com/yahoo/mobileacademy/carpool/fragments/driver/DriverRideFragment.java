package com.yahoo.mobileacademy.carpool.fragments.driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.mobileacademy.carpool.R;

public class DriverRideFragment extends Fragment {
	
	private View mView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    
		if (mView == null) {
		       mView = inflater.inflate(R.layout.fragment_driver_ride, container, false); 
		} else {
		   // Avoid the view to be recreated upon tab switching!
		   // The following code is required to prevent a Runtime exception
		   ((ViewGroup) mView.getParent()).removeView(mView);
		}
			
        return mView;
	        
	}
		
}
