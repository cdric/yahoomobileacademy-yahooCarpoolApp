package com.yahoo.mobileacademy.carpool.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.facebook.widget.ProfilePictureView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.DriverActivity;
import com.yahoo.mobileacademy.carpool.constants.AppConstants;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class DriverRideDetailsFragment extends Fragment {
	
	private OnDriverRideDetailsFragmentListene listener;
	
	public interface OnDriverRideDetailsFragmentListene {
		public void onSaveRideEvent(Ride r);
		public void onDeleteRideEvent(ParseObject ride);
		public void rideIsDefined(ParseObject ride);
	}
	
	private EditText etDriverRideStart;
	private Spinner spNbPassengers;
	private Spinner spDriverDestination;
	private ProfilePictureView pvUserProfilePicture;
	
	private ParseObject poRide;
	
	private Button btnSave;
	private Button btnDelete;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_driver_ride_details, container, false);
	    return view; 
	}
	
	@Override
	public void onAttach(Activity activity) { super.onAttach(activity);
	if (activity instanceof OnDriverRideDetailsFragmentListene) {
	      listener = (OnDriverRideDetailsFragmentListene) activity;
	    } else {
	      throw new ClassCastException("Must implement " + OnDriverRideDetailsFragmentListene.class.getName());
	    }
	}
	
	public void onDetach() { 
		super.onDetach(); 
		listener = null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpFragmentView();
		
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		pvUserProfilePicture.setProfileId(user.getFacebookId());
		
		((DriverActivity) getActivity()).onTimePicked(Calendar.getInstance());
		
		// Load Ride data
		loadRideDetails();
	}

	private void setUpFragmentView() {
		
		spNbPassengers = (Spinner) UtilityClass.initSpinnerWithRightTextAlignment(
				getActivity().getBaseContext(), 
				(Spinner) getActivity().findViewById(R.id.sp_drivers_nb_passengers),
				R.array.ride_occupency_values
		);
		
		spDriverDestination = (Spinner) UtilityClass.initSpinnerWithRightTextAlignment(
				getActivity().getBaseContext(), 
				(Spinner) getActivity().findViewById(R.id.sp_driver_destination),
				R.array.ride_destinations_values
		);
		  
		pvUserProfilePicture = (ProfilePictureView) getActivity().findViewById(R.id.pv_userProfilePicture);
		
		etDriverRideStart = (EditText) getActivity().findViewById(R.id.et_driver_time_ride_start);
		etDriverRideStart.setOnClickListener(new OnClickListener() {
			
	        @Override
	        public void onClick(View v)
	        {
	            // show the time picker dialog
	            DialogFragment newFragment = new TimePickerFragment();
	            newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
	        }
	        
	    }); 
		
		etDriverRideStart = (EditText) getActivity().findViewById(R.id.et_driver_time_ride_start);
		
		btnSave = (Button) getActivity().findViewById(R.id.btnSave);
		btnDelete = (Button) getActivity().findViewById(R.id.btnDelete);
		
	}
	
	/**
	 * Load existing ride details for this Driver
	 */
	private void loadRideDetails() { 
		
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		
		ParseQuery<ParseObject>userQuery = ParseQuery.getQuery("User");
		userQuery.whereEqualTo("id", user.getFacebookId());
		ParseQuery<ParseObject>driverQuery = ParseQuery.getQuery("Driver");
		driverQuery = driverQuery.whereMatchesQuery("userRef", userQuery);
		ParseQuery<ParseObject>rideQuery = ParseQuery.getQuery("Ride");
		rideQuery = rideQuery.whereMatchesQuery("driverRef", driverQuery);
		rideQuery.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> results, ParseException e) {
		        AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
			    //Log.d("debug", "driverQuery inner join map on User.Facebookid=" + user.getFacebookId() + ": " + results.size());
			    
			    if (results.size() > 0) {
			    	// Update ride details based on information stories in DB
			    	
			    	// Assuming only one result for users
			    	poRide = results.get(0);
			    	Ride ride = Ride.fromParseObject(poRide); 
			    	
			    	//Toast.makeText(getActivity().getBaseContext(), ride.getStartTime() + " / " + ride.getRideCapacity() + " / " + ride.getDestination(), Toast.LENGTH_SHORT).show();
			    	
			    	etDriverRideStart.setText(UtilityClass.formatDate(AppConstants.DRIVER_RIDE_DATE_FORMAT, ride.getStartTime()));
			    	spNbPassengers.setSelection(ride.getRideCapacity() -1);
			    	spDriverDestination.setSelection(UtilityClass.findValuePositionInArray(ride.getDestination(), getResources().getStringArray(R.array.ride_destinations_values)));
			    	
			    	// Disable creation of new ride
			    	setUIForRideEditMode(false);
			    	
			    	listener.rideIsDefined(poRide);
			    	
			    } else {
			    	// No ride have been define for this user 
			    }
			}
			
		});
		
	}
	
	// BUTTON ACTIONS
	
	public void onSaveRideAction(View v) {

		//TODO: To encapsulate in a dedicated method
		AuthenticatedUser user = UtilityClass.getAuthenticatedUser();
		Driver d = new Driver();
		d.setId(user.getFacebookId());
		d.setName(user.getName()); // TODO: We should remove this and get it from the 
		                           // Facebook API if possible as part of the user profile
		
		Ride r = new  Ride();
		r.setDestination(spDriverDestination.getSelectedItem().toString());
		r.setStartTime(UtilityClass.parseDate(AppConstants.DRIVER_RIDE_DATE_FORMAT, etDriverRideStart.getText().toString()));
		r.setRideCapacity(spNbPassengers.getSelectedItemPosition()+1);
		r.setDriver(d);
		
		listener.onSaveRideEvent(r);
		setUIForRideEditMode(false);
		
	}
	
	public void onDeleteRideAction(View v) {
		
		// Reset the UI
		setUIForRideEditMode(true);
		
		// Delete the ride for this user 
		listener.onDeleteRideEvent(poRide);
		
	}
	
	// PRIVATE METHODS

	/**
	 * Update the UI element on this fragment to allow the ride
	 * details to be editable (or not)
	 * @param isEditable should the ride be editable?
	 */
	private void setUIForRideEditMode(boolean isEditable) {
		if (isEditable) {
			btnSave.setEnabled(true);
			btnDelete.setEnabled(false);
			etDriverRideStart.setEnabled(true);
			spDriverDestination.setEnabled(true);
			spNbPassengers.setEnabled(true);
			
			etDriverRideStart.setText(UtilityClass.formatDate(AppConstants.DRIVER_RIDE_DATE_FORMAT, new Date()));
			spDriverDestination.setSelection(0);
			spNbPassengers.setSelection(0);
		} else {	
			btnSave.setEnabled(false);
			btnDelete.setEnabled(true);
			etDriverRideStart.setEnabled(false);
			spDriverDestination.setEnabled(false);
			spNbPassengers.setEnabled(false);
			
			
		}
	}
	
}
