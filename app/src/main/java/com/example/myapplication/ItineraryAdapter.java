package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItineraryTask> ItineraryTasks;


    public ItineraryAdapter (Context c, ArrayList<ItineraryTask> p){
        context = c;
        ItineraryTasks = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itinerary_item,parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.itineraryActivity.setText(ItineraryTasks.get(i).getItineraryActivity());
        myViewHolder.itineraryPrice.setText("Price: " + ItineraryTasks.get(i).getItineraryPrice());
        myViewHolder.itineraryRating.setText("Rating: " + ItineraryTasks.get(i).getItineraryRating());
        try{
            myViewHolder.ratingBar.setRating( Float.parseFloat(ItineraryTasks.get(i).getItineraryRating()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        myViewHolder.itineraryDate.setText(ItineraryTasks.get(i).getItineraryDate());

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        myViewHolder.itinerary_Date.setText(formatter.format(date));

        final String getItineraryActivity = ItineraryTasks.get(i).getItineraryActivity();
        final String getItineraryPrice = ItineraryTasks.get(i).getItineraryPrice();
        final String getItineraryRating = ItineraryTasks.get(i).getItineraryRating();
        final String getItineraryKey = ItineraryTasks.get(i).getItineraryKey();
        final String getItineraryDate = ItineraryTasks.get(i).getItineraryDate();


        myViewHolder.setIsRecyclable(false);

        myViewHolder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(context)
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Continue with delete operation
//                    Toast.makeText(context, "LONGGGG CLICK", Toast.LENGTH_SHORT).show();
                    ItineraryTasks.remove(i);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Itinerary");
                    ref.child(getItineraryKey).removeValue();
                    Intent intent =new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition (0, 0);

                }
            })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .show();

            return false;
        });

    }


    @Override
    public int getItemCount() {
        return ItineraryTasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itineraryActivity;
        TextView itineraryPrice;
        TextView itineraryRating;
        TextView itineraryKey;
        TextView itineraryDate;
        RatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itineraryActivity = itemView.findViewById(R.id.itinerary_activity);
            itineraryActivity.setSelected(true);
            itineraryPrice = itemView.findViewById(R.id.itinerary_price);
            itineraryRating = itemView.findViewById(R.id.itinerary_rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            itineraryDate = itemView.findViewById(R.id.itinerary_date);

        }
    }
}
