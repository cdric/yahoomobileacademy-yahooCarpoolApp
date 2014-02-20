package com.yahoo.mobileacademy.carpool.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.Notification;

/**
 * Custom adapter for the Notification list view
 * 
 * @author C�dric Lignier <cedric.lignier@free.fr>
 *
 */
public class NotificationsAdapter extends ArrayAdapter<Notification> {
	
	NotificationsAdapter mAdapter;
	
	public NotificationsAdapter(Context context, List<Notification> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View mView, ViewGroup parent) {
		mAdapter = this;
		
		if (mView == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflatter.inflate(R.layout.listview_notifications, null);  

		}

		final Notification notification = getItem(position);
					
    	TextView nameView = (TextView) mView.findViewById(R.id.tvNotificationMessage);
    	String formattedName;
    	if (notification.getNotificationMessage() != null) {
    		if (!notification.hasBeenRead()) {
    			formattedName = notification.getNotificationMessage();
			} else {
				formattedName = "<b>" + notification.getNotificationMessage() + "</b>";
    		}
    	} else {
    		formattedName = "<i>This notification does not have a message associated with it.</i>";      
    	}
    	nameView.setText(Html.fromHtml(formattedName));
    	
		ImageView imageView = (ImageView) mView.findViewById((R.id.ivProfile));
		ImageLoader.getInstance().displayImage(
		   UtilityClass.getDisplayImageURLForFacebookId(notification.getFromUserId()), imageView);
				
		Button btnDismissNotification = (Button) mView.findViewById(R.id.btnDismissNotification);
		btnDismissNotification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				notification.deleteInBackground(new DeleteCallback() {
					
					@Override
					public void done(ParseException e) {
						
						if (e == null) {
						
							mAdapter.remove(notification);
							mAdapter.notifyDataSetChanged();
						
						} else {
							
							// An error occurred
							Toast.makeText(getContext(), "This notification cannot be dismissed at this time. Please try agian later [Error: " + e.getMessage() + "]", Toast.LENGTH_SHORT).show();
						
						}
						
					}
					
				});
					
			}
			
		});
		
	   return mView;
	  
	}

}
