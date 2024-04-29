package com.example.eventpro;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class Contactus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_EventPro);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);


        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    private void navigateUpToMain() {
        // Navigate up to the main activity
        NavUtils.navigateUpFromSameTask(this);
    }

}

