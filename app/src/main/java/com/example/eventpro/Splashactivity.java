package com.example.eventpro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splashactivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 5000; // Splash screen duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // Delayed handler to navigate to the main activity after the splash duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent mainIntent = new Intent(Splashactivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the splash activity
                finish();
            }
        }, SPLASH_DURATION);
    }
}

