package com.example.eventpro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AddEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText("Add Event");

    }
}
