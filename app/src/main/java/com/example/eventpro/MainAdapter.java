package com.example.eventpro;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.event_name.setText(model.getEventname());
        holder.details.setText(model.getDetails());
        holder.org.setText(model.getOrganizers());
        holder.venue.setText(model.getVenue());

        Glide.with(holder.img.getContext())
                .load(model.getPurl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.img);

        // Button click listener
        holder.btn_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the venue of the event
                String venue = model.getVenue();

                // Start GetDirectionActivity and pass the venue information
                Intent intent = new Intent(v.getContext(), GetDirectionActivity.class);
                intent.putExtra("venue", venue);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        Button btn_calendar, btn_direction, btn_weather, btn_feedback;
        TextView event_name, details, venue, org;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_name);
            org = itemView.findViewById(R.id.org);
            details = itemView.findViewById(R.id.details);
            venue = itemView.findViewById(R.id.venue);
            img = itemView.findViewById(R.id.image_url);
            btn_calendar = itemView.findViewById(R.id.btn_calendar);
            btn_direction = itemView.findViewById(R.id.btn_direction);
            btn_weather = itemView.findViewById(R.id.btn_weather);
            btn_feedback = itemView.findViewById(R.id.btn_feedback);

            btn_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Calendar.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            btn_weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), cityFinder.class);
                    itemView.getContext().startActivity(intent);
                }
            });

            btn_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), Feedback.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
