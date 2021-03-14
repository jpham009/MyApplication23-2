package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItineraryTask> itineraryTasks;

    public ItineraryAdapter (Context c, ArrayList<ItineraryTask> p){
        context = c;
        itineraryTasks = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itinerary_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.itineraryTitle.setText(itineraryTasks.get(i).getItineraryTitle());
        myViewHolder.itineraryDescription.setText(itineraryTasks.get(i).getItineraryDescription());
        myViewHolder.itineraryDate.setText(itineraryTasks.get(i).getItineraryDate());

        final String getItineraryTitle = itineraryTasks.get(i).getItineraryTitle();
        final String getItineraryDescription = itineraryTasks.get(i).getItineraryDescription();
        final String getItineraryDate = itineraryTasks.get(i).getItineraryDate();
        final String getItineraryKey = itineraryTasks.get(i).getItineraryKey();

        myViewHolder.itemView.setOnClickListener(v -> {
            Intent aa = new Intent(context,ItineraryAdapter.class);
            aa.putExtra("itineraryTitle", getItineraryTitle);
            aa.putExtra("itineraryDescription", getItineraryDescription);
            aa.putExtra("itineraryDate", getItineraryDate);
            aa.putExtra("itineraryKey", getItineraryKey);
            context.startActivity(aa);
        });
    }

    @Override
    public int getItemCount() {
        return itineraryTasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itineraryTitle;
        TextView itineraryDescription;
        TextView itineraryDate;
        TextView itineraryKey;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itineraryTitle = itemView.findViewById(R.id.itinerary_title);
            itineraryDescription = itemView.findViewById(R.id.itinerary_description);
            itineraryDate = itemView.findViewById(R.id.itinerary_date);
        }
    }


}
