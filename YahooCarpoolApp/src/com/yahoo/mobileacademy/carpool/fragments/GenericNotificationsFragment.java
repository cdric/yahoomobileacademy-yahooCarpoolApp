package com.yahoo.mobileacademy.carpool.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.NotificationsAdapter;
import com.yahoo.mobileacademy.carpool.fragments.DriverRideDetailsFragment.OnDriverRideDetailsFragmentListener;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Notification;
import com.yahoo.mobileacademy.carpool.models.Ride;

public class GenericNotificationsFragment extends Fragment {
		
	private OnGenericNotificationsFragment listener;
	
	public interface OnGenericNotificationsFragment {
		public void onGenericNotificationsFragmentReady(GenericNotificationsFragment f);
	}
	
	private ListView lvNotifications;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_generic_notifications_list, container, false);
	    return view;  
	}
	
	@Override
	public void onAttach(Activity activity) { 
		super.onAttach(activity);
		if (activity instanceof OnGenericNotificationsFragment) {
		      listener = (OnGenericNotificationsFragment) activity;
	    } else {
	      throw new ClassCastException("Must implement " + OnGenericNotificationsFragment.class.getName());
	    }
	}
	
	public void onDetach() { 
		super.onDetach(); 
		listener = null;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupFragmentView();
		
		loadNotifications();
	}

	private void loadNotifications() {
		
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		ParseQuery<ParseObject>rideQuery = ParseQuery.getQuery("Notification");
		rideQuery.whereEqualTo("toUserId", authUser.getFacebookId());
		rideQuery.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> results, ParseException e) {
		        
				if (results.size() > 0) {
			    	// Update ride details based on information stories in DB
					updateNotificationAdapater((List<Notification>)(List<?>) results);
			    	
			    } else {
			    	
			    	// No notifications exits for this user
			    	Toast.makeText(getActivity().getBaseContext(), "No notifications. All good!", Toast.LENGTH_SHORT).show();
			    }
			}
			
		});
		
	}

	private void setupFragmentView() {
		lvNotifications = (ListView) getActivity().findViewById(R.id.lvNotifications);
		listener.onGenericNotificationsFragmentReady(this); 
	}



	/**
	 * Update the list view with all passenger
	 *  - The method will create the adapter if it does not exist yet
	 * @param passengers the List of Passengers to add
	 */
	public void updateNotificationAdapater(List<Notification> notifications) {
		
		NotificationsAdapter adapter = (NotificationsAdapter)lvNotifications.getAdapter();
		
		if (adapter == null) {
			adapter = new NotificationsAdapter(getActivity().getBaseContext(), notifications);
			lvNotifications.setAdapter(adapter);
		} else {
			adapter.addAll(notifications);
		}

	}

}