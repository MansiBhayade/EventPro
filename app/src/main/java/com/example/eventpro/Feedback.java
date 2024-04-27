package com.example.eventpro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity {

    private EditText feedback1, feedback2;
    private RatingBar ratingBarSession;
    private Button buttonSubmitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        // Initialize views
        feedback1 = findViewById(R.id.feedback1);
        feedback2 = findViewById(R.id.feedback2);
        ratingBarSession = findViewById(R.id.ratingBarSession);
        buttonSubmitFeedback = findViewById(R.id.buttonSubmitFeedback);

        // Set onClickListener for the submit button
        buttonSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
                feedback1.getText().clear();
                feedback2.getText().clear();
                ratingBarSession.setRating(0.0f);
            }
        });
    }

    // Method to handle submitting feedback
    private void submitFeedback() {
        // Get values from EditText and RatingBar
        String name = feedback1.getText().toString().trim();
        String comments = feedback2.getText().toString().trim();
        float rating = ratingBarSession.getRating();

        // You can process the feedback here, for now, let's just display it in a toast message
        String feedbackMessage = "Your  following feedback is recorded" + "\nEvent Name: " + name + "\nComments: " + comments + "\nRating: " + rating;
        Toast.makeText(this, feedbackMessage, Toast.LENGTH_LONG).show();
    }
}
