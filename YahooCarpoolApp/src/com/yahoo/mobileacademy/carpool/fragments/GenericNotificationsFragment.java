package com.yahoo.mobileacademy.carpool.fragments;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.NotificationsAdapter;
import com.yahoo.mobileacademy.carpool.fragments.base.AbstractBaseFragment;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.listeners.AsyncFragmentRefreshListener;
import com.yahoo.mobileacademy.carpool.models.AuthenticatedUser;
import com.yahoo.mobileacademy.carpool.models.Notification;

public class GenericNotificationsFragment extends AbstractBaseFragment {
		
	private AsyncFragmentRefreshListener listener;
	
	private ListView lvNotifications;
	private TextView tvNoResults;
	private View mView;
	
	//TODO: Improve mecanism to defect if view fragment to be refreshed
	private boolean hasNotificationAlreadyLoaded = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (mView == null) {
		       mView = inflater.inflate(R.layout.fragment_generic_notifications_list, container, false); 
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
		
		if (hasNotificationAlreadyLoaded == false) { //TODO: Only reload if required 
			loadNotifications();
		}
	}

	private void loadNotifications() {
		
		hasNotificationAlreadyLoaded = true;
		listener.asyncContentRefreshTaskStarted("Loading...");
		//showProgressBar();
		
		AuthenticatedUser authUser = UtilityClass.getAuthenticatedUser();
		
		ParseQuery<ParseObject>rideQuery = ParseQuery.getQuery("Notification");
		rideQuery.whereEqualTo("toUserId", authUser.getFacebookId());
		rideQuery.findInBackground(new FindCallback<ParseObject>() {
			
			public void done(List<ParseObject> results, ParseException e) {
		        
				if (results.size() > 0) {
			    	// Update ride details based on information stories in DB
					updateNotificationAdapater((List<Notification>)(List<?>) results);
			    	
			    } else {
			    	
			    	listener.asyncContentRefreshTaskCompleted();
			    	
			    	// No notifications exits for this user
			    	// Toast.makeText(getActivity().getBaseContext(), "No notifications. All good!", Toast.LENGTH_SHORT).show();
			    }
				
			}
			
		});
		
	}

	private void setupFragmentView() {
		
		tvNoResults = (TextView) getView().findViewById(R.id.tv_fragment_noResults);
		
		lvNotifications = (ListView) getActivity().findViewById(R.id.lvNotifications);
		lvNotifications.setEmptyView(tvNoResults);

	}



	/**
	 * Update the list view with all passenger
	 *  - The method will create the adapter if it does not exist yet
	 * @param passengers the List of Passengers to add
	 */
	public void updateNotificationAdapater(List<Notification> notifications) {
		
		NotificationsAdapter adapter = (NotificationsAdapter)lvNotifications.getAdapter();
		
		if (adapter == null) {
			adapter = new NotificationsAdapter(this, notifications);
			lvNotifications.setAdapter(adapter);
		} else {
			adapter.addAll(notifications);
		}

	}

}
