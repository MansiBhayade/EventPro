package com.example.eventpro;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class Weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Update the TextView with weather information
        TextView textViewWeather = findViewById(R.id.textViewWeather);
        textViewWeather.setText("Current Weather: Sunny");
    }
}
