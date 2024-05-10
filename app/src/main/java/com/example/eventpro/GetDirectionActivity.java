package com.example.eventpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetDirectionActivity extends AppCompatActivity {

    private EditText sourceLocation, destinationLocation;
    private Button btnNav;

    private String eventVenue;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_direction);

        sourceLocation = findViewById(R.id.source_location);
        destinationLocation = findViewById(R.id.destination_location);
        btnNav = findViewById(R.id.btn_submit);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Retrieve venue information passed from MainActivity
        eventVenue = getIntent().getStringExtra("venue");

        // Set the destination location EditText to the event venue
        destinationLocation.setText(eventVenue);

        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the source location from the EditText
                String source = sourceLocation.getText().toString();

                // Get the destination location from the eventVenue
                String destination = eventVenue;

                // Create the Google Maps URI
                Uri uri = Uri.parse("https://www.google.com/maps/dir/" + source + "/" + destination);

                // Create and start the intent to open Google Maps
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // Fetch user's current location as the default source location
        fetchCurrentLocation();
    }

    private void fetchCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                // If the last known location is available, set it as the source location
                sourceLocation.setText(location.getLatitude() + ", " + location.getLongitude());
            } else {
                // If the last known location is not available, request location updates
                fusedLocationProviderClient.requestLocationUpdates(createLocationRequest(), new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if (locationResult != null) {
                            for (android.location.Location loc : locationResult.getLocations()) {
                                sourceLocation.setText(loc.getLatitude() + ", " + loc.getLongitude());
                                break; // Only update once with the first available location
                            }
                        }
                    }
                }, null);
            }
        });
    }

    private com.google.android.gms.location.LocationRequest createLocationRequest() {
        return new com.google.android.gms.location.LocationRequest()
                .setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000); // Update location every 10 seconds
    }
}
