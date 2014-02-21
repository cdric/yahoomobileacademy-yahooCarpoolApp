package com.yahoo.mobileacademy.carpool.asynctasks;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.parse.ParseException;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.adapters.RideSearchResultsAdapter;
import com.yahoo.mobileacademy.carpool.adapters.models.RideSearchResult;
import com.yahoo.mobileacademy.carpool.fragments.PassengerSearchRideFragment.OnPassengerSearchRideFragmentListener;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.Driver;
import com.yahoo.mobileacademy.carpool.models.Ride;

/**
 * Build a list of RideSearchResult for the Adapter 
 * from a list of Ride ParseObjects
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class CreateRideSearchResultListAsyncTask extends AsyncTask<List<Ride>, Void, List<RideSearchResult>> {

	FragmentActivity mFragment;
	OnPassengerSearchRideFragmentListener mListener;
	
	public CreateRideSearchResultListAsyncTask(FragmentActivity fragmentActivity, OnPassengerSearchRideFragmentListener listener) {
		
		mFragment = fragmentActivity;
		mListener = listener;
		
	}
	
	@Override
	protected List<RideSearchResult> doInBackground(List<Ride>... params) {

		List<RideSearchResult> results = new ArrayList<RideSearchResult>();
		
		for (final Ride ride: params[0]) {
		
			Driver driver;
			try {
				driver = ride.getDriver().fetchIfNeeded();
				
				RideSearchResult result = new RideSearchResult();		        	
	            result.setDriverName(driver.getName());
	            result.setRideStartTime(ride.getStartTime());
	    		result.setProfileImage(UtilityClass.getDisplayImageURLForFacebookId(driver.getUserId()));
	    		result.setRideFull(Ride.isRideFull(ride));
	    		result.setDriverUserId(driver.getUserId());
	    		result.setRide(ride);
	    		
	    		results.add(result);
	    		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		return results;
		
	}
	
	@Override
    protected void onPostExecute(List<RideSearchResult> results) { 
		
		ListView lvSearchResults = (ListView) mFragment.findViewById(R.id.lvSearchResults);
		RideSearchResultsAdapter adapter = (RideSearchResultsAdapter)lvSearchResults.getAdapter();
		
		if (adapter == null) {
			adapter = new RideSearchResultsAdapter(mFragment.getBaseContext(), results);
			lvSearchResults.setAdapter(adapter);
		} else {
			adapter.addAll(results);
		}
		
		mListener.asynTaskCompleted();
		
	}

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

}