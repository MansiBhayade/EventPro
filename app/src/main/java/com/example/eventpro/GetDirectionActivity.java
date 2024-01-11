package com.example.eventpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GetDirectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_direction);
        EditText source,destination;
        Button btn_nav;
        source = findViewById(R.id.source_location);
        destination = findViewById(R.id.destination_location);
        btn_nav = findViewById(R.id.btn_submit);

        btn_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_source = source.getText().toString();
                String text_dest = destination.getText().toString();

                Uri uri = Uri.parse("https://www.google.com/maps/dir/" + text_source + "/"+ text_dest);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
