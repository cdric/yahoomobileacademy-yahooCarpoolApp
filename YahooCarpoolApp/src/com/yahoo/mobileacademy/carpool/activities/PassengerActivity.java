package com.yahoo.mobileacademy.carpool.activities;

import android.os.Bundle;
import android.view.Menu;

import com.yahoo.mobileacademy.carpool.R;
import com.yahoo.mobileacademy.carpool.activities.base.AbstractRoleActivity;

public class PassengerActivity extends AbstractRoleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        
        setUpActivity();
    }

    private void setUpActivity() {
    	updateActionBarTitle(R.string.passenger);
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passenger, menu);
        return true;
    }
    
    
    
}
