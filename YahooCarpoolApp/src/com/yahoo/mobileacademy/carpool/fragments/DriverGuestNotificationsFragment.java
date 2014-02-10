package com.yahoo.mobileacademy.carpool.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.mobileacademy.carpool.R;

public class DriverGuestNotificationsFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_driver_ride_notifications_list, container, false);
	    return view; 
	}

}
