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

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.models.Passenger;

/**
 * Custom adapter for the Passenger Summary List item
 * 
 * @author CŽdric Lignier <cedric.lignier@free.fr>
 *
 */
public class PassengerSummaryAdapter extends ArrayAdapter<Passenger> {
	
	public PassengerSummaryAdapter(Context context, List<Passenger> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {

			LayoutInflater inflatter = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflatter.inflate(R.layout.guest_profile_summary, null);

		}

		final Passenger passenger = getItem(position);

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + passenger.getName() + "</b>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView emailView = (TextView) view.findViewById(R.id.tvEmail);
		String formattedEmail = passenger.getEmail();
		emailView.setText(Html.fromHtml(formattedEmail));
		
		TextView phoneView = (TextView) view.findViewById(R.id.tvPhone);
		String formattedPhone = passenger.getPhoneNumber();
		phoneView.setText(Html.fromHtml(formattedPhone));
		
		ImageView imageView = (ImageView) view.findViewById((R.id.ivProfile));
//		ImageLoader.getInstance().displayImage(
//				passenger.getProfileImageUrl(), imageView);

		//Toast.makeText(getContext(), "Generate passenger entry: " + passenger.getName(), Toast.LENGTH_SHORT).show();
		
		return view;
	}

}
