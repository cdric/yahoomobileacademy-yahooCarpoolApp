package com.yahoo.mobileacademy.carpool.fragments.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.yahoo.mobileacademy.carpool.R;

public abstract class AbstractBaseFragment extends Fragment {
	
	protected ProgressBar pb;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pb = (ProgressBar) getActivity().findViewById(R.id.pb_loading);
	}

	/**
     * Show the progress bar dialog
     */
    public void showProgressBar() {
    	pb.setVisibility(ProgressBar.VISIBLE);
    }
    
    /**
     * Hide the progress bar dialog
     */
    public void hideProgressBar() {
    	pb.setVisibility(ProgressBar.INVISIBLE);
    }

}
