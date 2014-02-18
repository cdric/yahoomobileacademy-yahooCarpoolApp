package com.yahoo.mobileacademy.carpool.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.RideGuestsAdapter;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Passenger;
import com.yahoo.mobileacademy.carpool.models.Ride;

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
		loadRideDetails();
	}

	private void setupFragmentView() {
		lvPassengersList = (ListView) getView().findViewById(R.id.lvPassengerList);
		
	}
	
	private void loadRideDetails() {
		
		//TODO: Can we refactor this code with the similar method in DriverRideDetailsFragment.java?
		
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		
		ParseQuery<ParseObject>driverQuery = ParseQuery.getQuery("Driver");
		driverQuery.whereEqualTo("userId", user.getFacebookId());
		ParseQuery<ParseObject>rideQuery = ParseQuery.getQuery("Ride");
		rideQuery = rideQuery.whereMatchesQuery("driver", driverQuery);
		rideQuery.include("passengerList");
		rideQuery.getFirstInBackground(new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {

				if (e == null) {
			    	
					Ride ride = (Ride) object;
					if (ride.getPassengers().isEmpty()) {
						
						// No passengers in this ride yet
						Toast.makeText(getActivity().getBaseContext(), "It looks like you will be riding home on your own :-(", Toast.LENGTH_SHORT).show();
						
					} else {
			    	   updatePassengerAdapter(((Ride) object).getPassengers());
					}
			    	
			    } else {
			    	
			    	// An error occurred
					Toast.makeText(getActivity().getBaseContext(), "Can't fetch the list of passengers for your current ride. Please try agian later [Error: " + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
				    	
			    }
				
			}
		});
		
	}



	



	/**
	 * Update the list view with all passenger
	 *  - The method will create the adapter if it does not exist yet
	 * @param passengers the List of Passengers to add
	 */
	private void updatePassengerAdapter(List<Passenger> passengers) {
		
		RideGuestsAdapter adapter = (RideGuestsAdapter)lvPassengersList.getAdapter();
		
		if (adapter == null) {
			adapter = new RideGuestsAdapter(getActivity().getBaseContext(), passengers);
			lvPassengersList.setAdapter(adapter);
		} else {
			adapter.clear();
			adapter.addAll(passengers);
		}

	}

}
