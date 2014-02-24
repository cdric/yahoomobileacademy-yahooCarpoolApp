package com.yahoo.mobileacademy.carpool.fragments.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import com.yahoo.mobileacademy.carpool.R;

/**
 * Base class for all fragment of this application
 *  - Provide support for progress bar and associated utility methods
 *  
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public abstract class AbstractBaseFragment extends Fragment {
	
	protected ProgressBar pb;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
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
