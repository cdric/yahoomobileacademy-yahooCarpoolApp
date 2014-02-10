package com.yahoo.mobileacademy.carpool.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.PassengerSummaryAdapter;
import com.yahoo.mobileacademy.carpool.models.Passenger;

public class DriverPassengerListFragment extends Fragment {
	
	ListView lvPassengersList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_driver_passengers_list, container, false);
	    return view; 
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupFragmentView();
		populateWithFakeData();
	}

	private void populateWithFakeData() {
		
		Passenger p1 = new Passenger();
		p1.setName("Cedric Lignier");
		p1.setEmail("lignierc@yahoo-inc.com");
		p1.setPhoneNumber("415-215-1234");
		//p1.setProfileImageUrl("https://pbs.twimg.com/profile_images/429046548581072897/QyJvSxPY.jpeg");

		Passenger p2 = new Passenger();
		p2.setName("Cheng Xu");
		p2.setEmail("chengxu@yahoo-inc.com");
		p2.setPhoneNumber("408-336-5151");
		//p2.setProfileImageUrl("https://pbs.twimg.com/profile_images/429046548581072897/QyJvSxPY.jpeg");

		List<Passenger> plist = new ArrayList<Passenger>();
		plist.add(p1);
		plist.add(p2);
		updatePassengerList(plist);

		
	}



	private void setupFragmentView() {
		lvPassengersList = (ListView) getActivity().findViewById(R.id.lvPassengerList);
		
	}



	/**
	 * Update the list view with all passenger
	 *  - The method will create the adapter if it does not exist yet
	 * @param passengers the List of Passengers to add
	 */
	public void updatePassengerList(List<Passenger> passengers) {
		
		PassengerSummaryAdapter adapter = (PassengerSummaryAdapter)lvPassengersList.getAdapter();
		
		if (adapter == null) {
			adapter = new PassengerSummaryAdapter(getActivity().getBaseContext(), passengers);
			lvPassengersList.setAdapter(adapter);
		} else {
			adapter.addAll(passengers);
		}

	}

}
