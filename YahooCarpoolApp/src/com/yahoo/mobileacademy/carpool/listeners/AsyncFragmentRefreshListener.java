package com.yahoo.mobileacademy.carpool.listeners;

public interface AsyncFragmentRefreshListener {

	/**
	 * Method called with an async task to update the content
	 * of the fragment has been fired
	 * 
	 * This will usually add a progress indicator
	 * to the activity
	 * 
	 * @param refreshStatus the message to be display on the progress bar
	 */
	public void asyncContentRefreshTaskStarted(String refreshStatus);
	
	/**
	 * Method called when the async task to update the content
	 * of the fragment has completed
	 * 
	 * This will usually remove any progress indicator
	 * from the activity
	 */
	public void asyncContentRefreshTaskCompleted();
	
}
