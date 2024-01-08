package com.example.eventpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
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
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView event_name,details,venue,org;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

             event_name = itemView.findViewById(R.id.event_name);
             org = itemView.findViewById(R.id.org);
             details = itemView.findViewById(R.id.details);
             venue = itemView.findViewById(R.id.venue);
             img = itemView.findViewById(R.id.image_url);
        }
    }
}
