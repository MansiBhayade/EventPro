package com.example.eventpro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calendar extends AppCompatActivity {
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button myButton = findViewById(R.id.myButton);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Handle the selected date change if needed
                // For example, you can display a Toast with the selected date
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(Calendar.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener for the AddEvent Button
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to add the event to the calendar
                addEventToCalendar();
            }
        });
    }

    private void addEventToCalendar() {
        // Get the selected date from the CalendarView
        long selectedDateMillis = calendarView.getDate();

        // Create an intent to open the Calendar app
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Event Title");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Event Location");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Event Description");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDateMillis);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, selectedDateMillis + 60 * 60 * 1000); // Event duration (1 hour)

        // For devices running Android 6.0 (Marshmallow) and above, request the user's permission to write to the calendar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        // Start the Calendar app with the pre-filled event details
        startActivity(intent);
    }


}

