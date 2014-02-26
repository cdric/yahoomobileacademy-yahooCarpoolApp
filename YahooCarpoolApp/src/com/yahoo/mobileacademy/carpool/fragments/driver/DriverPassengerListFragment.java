package com.yahoo.mobileacademy.carpool.fragments.driver;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.driver.RideGuestsAdapter;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.listeners.AsyncFragmentRefreshListener;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class DriverPassengerListFragment extends Fragment {
	
	private AsyncFragmentRefreshListener listener;
	
	private View mView;
	private ListView lvPassengersList;
	private TextView tvNoResults;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (mView == null) {
		       mView = inflater.inflate(R.layout.fragment_driver_passengers_list, container, false); 
		} else {
		   // Avoid the view to be recreated upon tab switching!
		   // The following code is required to prevent a Runtime exception
		   ((ViewGroup) mView.getParent()).removeView(mView);
		}
			
     return mView;
      
	}
	
	@Override
	public void onAttach(Activity activity) { 
		super.onAttach(activity);
		
		if (activity instanceof AsyncFragmentRefreshListener) {
		      listener = (AsyncFragmentRefreshListener) activity;
	    } else {
	      throw new ClassCastException("Must implement " + AsyncFragmentRefreshListener.class.getName());
	    }
	}
	
	@Override
	public void onDetach() { 
		super.onDetach(); 
		listener = null; 
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupFragmentView();
		loadRideDetails();
	}

	private void setupFragmentView() {
		
		//TODO: Can provide a better name
		tvNoResults = (TextView) getView().findViewById(R.id.tv_fragment_noResults);
				
		lvPassengersList = (ListView) getView().findViewById(R.id.lvPassengerList);
		lvPassengersList.setEmptyView(tvNoResults);
		
	}
	
	private void loadRideDetails() {
		
		listener.asyncContentRefreshTaskStarted("Loading...");
		
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
						// lvPassengersList.setVisibility(ListView.INVISIBLE);
						// tvNoResults.setVisibility(TextView.VISIBLE);
						listener.asyncContentRefreshTaskCompleted();
						
					} else {
						
			    	   updatePassengerAdapter((Ride) object);
			    	   
					}
			    	
			    } else {
			    	
			    	// An error occurred OR No results were found
					//Toast.makeText(getActivity().getBaseContext(), "Can't fetch the list of passengers for your current ride. Please try agian later [Error: " + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
				    	
			    	// Ride hasn't been define
					// lvPassengersList.setVisibility(ListView.INVISIBLE);
					// tvNoResults.setVisibility(TextView.VISIBLE);
					
			    	listener.asyncContentRefreshTaskCompleted();
			    	
			    }
				
			}
		});
		
	}



	



	/**
	 * Update the list view with all passenger
	 *  - The method will create the adapter if it does not exist yet
	 * @param passengers the List of Passengers to add
	 */
	private void updatePassengerAdapter(Ride ride) {
		
		RideGuestsAdapter adapter = (RideGuestsAdapter)lvPassengersList.getAdapter();
		
		if (adapter == null) {
			adapter = new RideGuestsAdapter(getActivity().getBaseContext(), ride);
			lvPassengersList.setAdapter(adapter);
		} else {
			adapter.clear();
			adapter.addAll(ride.getPassengers());
		}
		
		listener.asyncContentRefreshTaskCompleted();

	}

}
