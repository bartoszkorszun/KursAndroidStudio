package com.example.carcheck;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
ACTIVITY FOR COUNTING AN AMOUNT OF TIME THAT CAR NEEDS TO ACCELERATE FROM 0 TO 100 KM/H
 */

public class GpsActivity extends AppCompatActivity implements LocationListener {

    private TextView currentSpeed;
    private TextView bestSpeed;
    private Button button;
    private int bestTimeTo100;
    private int secTo100;
    private LocationManager locationManager;
    private Date startTime;
    private boolean wasCounted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);

        currentSpeed = findViewById(R.id.current_speed);
        bestSpeed = findViewById(R.id.best_speed);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = new Date();
                wasCounted = false;
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (savedInstanceState != null) {

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {

        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 2.0f, this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        float speed = location.getSpeed();
        float kmhSpeed = speed * 1000 / 3600;
        currentSpeed.setText("Bierząca prędkość" + kmhSpeed + " KM/H");

        if (kmhSpeed >= 100 && !wasCounted) {
            long diffInMiliSec = new Date().getTime() - startTime.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMiliSec);
            bestSpeed.setText("Ostatni rekord" + diffInSec + " sekund");
        }
    }
}
