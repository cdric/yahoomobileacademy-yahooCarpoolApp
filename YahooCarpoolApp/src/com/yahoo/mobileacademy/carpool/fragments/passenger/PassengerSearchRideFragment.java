package com.yahoo.mobileacademy.carpool.fragments.passenger;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.PassengerActivity;
import com.yahoo.mobileacademy.carpool.adapters.passenger.RideSearchResultsAdapter;
import com.yahoo.mobileacademy.carpool.asynctasks.CreateRideSearchResultListAsyncTask;
import com.yahoo.mobileacademy.carpool.fragments.TimePickerFragment;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.listeners.AsyncFragmentRefreshListener;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class PassengerSearchRideFragment extends Fragment {
	
	private AsyncFragmentRefreshListener listener;
	
	private EditText etRideStart;
	private Spinner spRideOffset, spRideDestination, spRideBusinessunit;
	private ListView lvSearchResults;
	private TextView tvNoResults;
	
	private View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (mView == null) {
		       mView = inflater.inflate(R.layout.fragment_passenger_search, container, false); 
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
		setUpFragmentView();
		
		((PassengerActivity) getActivity()).onTimePicked(Calendar.getInstance());
	}

	private void setUpFragmentView() {
		
		etRideStart = (EditText) getActivity().findViewById(R.id.et_time_ride_start);
		etRideStart.setOnClickListener(new OnClickListener() {
			
	        @Override
	        public void onClick(View v)
	        {
	            // show the time picker dialog
	            DialogFragment newFragment = new TimePickerFragment();
	            newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
	        }
	        
	    }); 
		
		spRideOffset = (Spinner) UtilityClass.initSpinnerWithRightTextAlignment(
				getActivity().getBaseContext(), 
				(Spinner) getActivity().findViewById(R.id.sp_passenger_search_ride_offset),
				R.array.ride_offset_values
		);
		
		spRideDestination = (Spinner) UtilityClass.initSpinnerWithRightTextAlignment(
				getActivity().getBaseContext(), 
				(Spinner) getActivity().findViewById(R.id.sp_passenger_search_ride_destination),
				R.array.ride_destinations_values
		);
		
		spRideBusinessunit = (Spinner) UtilityClass.initSpinnerWithRightTextAlignment(
				getActivity().getBaseContext(), 
				(Spinner) getActivity().findViewById(R.id.sp_passenger_search_ride_businessunit),
				R.array.ride_business_unit_values
		);
		
		tvNoResults = (TextView) getActivity().findViewById(R.id.tv_fragment_noResults);
		
		lvSearchResults = (ListView) getActivity().findViewById(R.id.lvSearchResults);
		lvSearchResults.setEmptyView(tvNoResults);
		
	}
		
	// BUTTON ACTIONS
	
	/**
	 * Perform a ride search
	 * @param v the button 
	 */
	public void onSearchRideAction(View v) {
		
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		ParseQuery<ParseObject>rideQuery = ParseQuery.getQuery("Ride");
		rideQuery.whereEqualTo("destination", spRideDestination.getSelectedItem().toString());
	
		// Exclude current driver from the list of results
		ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Driver");
		innerQuery.whereEqualTo("userId", authUser.getFacebookId());
		rideQuery.whereDoesNotMatchQuery("driver", innerQuery);
	
		rideQuery.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> results, ParseException e) {
		        
				if (results.size() > 0) {
					
					RideSearchResultsAdapter adapter = (RideSearchResultsAdapter)lvSearchResults.getAdapter();
					if (adapter != null) {
						adapter.clear();	
					}
					
			    	// Update ride details based on information stories in DB
					new CreateRideSearchResultListAsyncTask(getActivity(), listener).execute((List<Ride>)(List<?>) results);	
			    	
			    } else {
			    	
			    	// No results for this user
			    	// lvSearchResults.setVisibility(ListView.INVISIBLE);
					// tvNoResults.setVisibility(TextView.VISIBLE);
					
					listener.asyncContentRefreshTaskCompleted();
					
			    }
				
			}
			
		});
		
	}
		
}
