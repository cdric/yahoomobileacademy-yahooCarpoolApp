package com.yahoo.mobileacademy.carpool.fragments.nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.fragments.base.AbstractBaseFragment;

public class CloseParentActivityFragment extends AbstractBaseFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_empty, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Close the parent activity
		getActivity().finish();
	}


}
