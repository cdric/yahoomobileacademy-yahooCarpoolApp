package com.yahoo.mobileacademy.carpool.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.helpers.UtilityClass;
import com.yahoo.mobileacademy.carpool.models.Passenger;

/**
 * Custom adapter for the Passenger list view
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class RideGuestsAdapter extends ArrayAdapter<Passenger> {
	
	public RideGuestsAdapter(Context context, List<Passenger> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflatter.inflate(R.layout.listview_ride_guests, null);

		}

		Passenger passenger = getItem(position);

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + passenger.getName() + "</b>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView emailView = (TextView) view.findViewById(R.id.tvEmail);
		String formattedEmail;
		if (passenger.getEmail() != null) {
		    formattedEmail = passenger.getEmail();
		} else {
			formattedEmail = "This user has not provided an email address";
		}
		emailView.setText(Html.fromHtml(formattedEmail));
		
		TextView phoneView = (TextView) view.findViewById(R.id.tvPhone);
		String formattedPhone;
		if (passenger.getPhoneNumber() != null) {
			formattedPhone = passenger.getPhoneNumber();
		} else {
			formattedPhone = "This user has not provided a phone number to be reached out to";
		}
		phoneView.setText(Html.fromHtml(formattedPhone));
		
		ImageView imageView = (ImageView) view.findViewById((R.id.ivProfile));
		ImageLoader.getInstance().displayImage(
				UtilityClass.getDisplayImageURLForFacebookId(passenger.getUserId()), imageView);

		return view;
	}

}
