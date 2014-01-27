package com.yahoo.mobileacademy.carpool.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.yahoo.mobileacademy.carpool.R;

public class PassengerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passenger, menu);
        return true;
    }
    
}
