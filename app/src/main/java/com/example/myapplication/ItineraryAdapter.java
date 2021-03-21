package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.itineraryTitle.setText(itineraryTasks.get(i).getItineraryTitle());
        myViewHolder.itineraryDescription.setText("Price: " + itineraryTasks.get(i).getItineraryDescription());
        myViewHolder.itineraryDate.setText("Rating: " + itineraryTasks.get(i).getItineraryDate());

        final String getItineraryTitle = itineraryTasks.get(i).getItineraryTitle();
        final String getItineraryDescription = itineraryTasks.get(i).getItineraryDescription();
        final String getItineraryDate = itineraryTasks.get(i).getItineraryDate();
        final String getItineraryKey = itineraryTasks.get(i).getItineraryKey();

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
                    itineraryTasks.remove(i);
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
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();

            return false;
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
            itineraryTitle = itemView.findViewById(R.id.itinerary_activity);
            itineraryDescription = itemView.findViewById(R.id.itinerary_description);
            itineraryDate = itemView.findViewById(R.id.itinerary_rating);

        }
    }
}
