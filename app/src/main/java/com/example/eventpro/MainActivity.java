package com.example.eventpro;

// Created By Mansi Bhayade

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ImageView loc_image;

    FloatingActionButton add_event;

    FusedLocationProviderClient fusedLocationProviderClient;

    MainAdapter mainAdapter;

    Button btnShowWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        setSupportActionBar(toolbar);

        add_event = findViewById(R.id.add_event);
        loc_image = findViewById(R.id.loc);
        btnShowWeather = findViewById(R.id.btnShowWeather);



        loc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        btnShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch WeatherActivity when the weather button is clicked
                launchWeather();
            }
        });




    // retrieve items from firebase
        RecyclerView recyclerView = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Event"), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

      add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open a new activity when the FAB is clicked
                Intent intent = new Intent(MainActivity.this, AddEvent.class);
                startActivity(intent);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                MainActivity.this
        );
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)==
                PackageManager.PERMISSION_GRANTED)
        {
           getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100
                    );
        }

    }

    private void showPopup(View anchorView, String locationText) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        TextView locTextView = popupView.findViewById(R.id.loc_text);

        // Set the text of the TextView with the location information
        locTextView.setText(locationText);

        // Set up the close button inside the pop-up
        ImageView closeButton = popupView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // Show the pop-up at the center of the screen
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    private void launchWeather() {
        // Start WeatherActivity when the weather button is clicked
        Intent weatherIntent = new Intent(MainActivity.this, Weather.class);
        startActivity(weatherIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                // Permission denied, handle accordingly
                // Add a dialog box showing this text "Permission denied. Please give Location access for app to work." and then
                // exit the app
                Toast.makeText(this, "Permission denied. Please give Location access for app to work.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String locationText = "Latitude: " + latitude + ", Longitude: " + longitude;
                        showPopup(loc_image, locationText);
                    } else {
                        LocationRequest locationRequest =  new com.google.android.gms.location.LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000); // Update location every 10 seconds
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        double latitude = location1.getLatitude();
                                        double longitude = location1.getLongitude();
                                        String newLocationText = "Latitude: " + latitude + ", " +
                                                "Longitude: " + longitude;
                                showPopup(loc_image, newLocationText);
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    }
                }
            });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mainAdapter.stopListening();
    }

}