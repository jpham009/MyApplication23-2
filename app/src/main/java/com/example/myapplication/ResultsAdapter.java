package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.transform.Result;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItineraryResult> ItineraryResults;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String resultKey = Integer.toString(doesNum);
    private AdapterView.OnItemClickListener mListener;


    public ResultsAdapter(Context c, ArrayList<ItineraryResult> p){
        context = c;
        ItineraryResults = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.result_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.resultsTitle.setText(ItineraryResults.get(i).getResultTitle());
        myViewHolder.resultsDescription.setText(ItineraryResults.get(i).getResultDescription());
        myViewHolder.resultsDate.setText(ItineraryResults.get(i).getResultDate());

        final String getResultTitle = ItineraryResults.get(i).getResultTitle();
        final String getResultDescription = ItineraryResults.get(i).getResultDescription();
        final String getResultDate = ItineraryResults.get(i).getResultDate();
        final String getResultKey = ItineraryResults.get(i).getResultKey();

//        myViewHolder.itemView.setOnClickListener(v -> {
//            Intent aa = new Intent(context, ResultsAdapter.class);
//            aa.putExtra("resultsTitle", getResultTitle);
//            aa.putExtra("resultsDescription", getResultDescription);
//            aa.putExtra("resultsDate", getResultDate);
//            aa.putExtra("resultsKey", getResultKey);
//            context.startActivity(aa);
//        });
    }

    @Override
    public int getItemCount() {
        return ItineraryResults.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView resultsTitle;
        TextView resultsDescription;
        TextView resultsDate;
        TextView resultsKey;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsTitle = itemView.findViewById(R.id.result_title);
            resultsDescription = itemView.findViewById(R.id.result_description);
            resultsDate = itemView.findViewById(R.id.result_date);
            Button addButton = itemView.findViewById(R.id.add_button);
            addButton.setOnClickListener(v -> {

//                //DATABASE
//                reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
//                        child("Does" + doesNum);
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        dataSnapshot.getRef().child("itineraryTitle").setValue(resultsTitle.getText().toString());
//                        dataSnapshot.getRef().child("itineraryDescription").setValue(resultsDescription.getText().toString());
//                        dataSnapshot.getRef().child("itineraryDate").setValue(resultsDate.getText().toString());
//                        dataSnapshot.getRef().child("itineraryKey").setValue(resultKey);
//                        addButton.setText("Clicked");
                        int adapterPosition = getAdapterPosition();
                        Log.i("ADAPTER POSITION" , String.valueOf(adapterPosition));
                        Log.i("PLACE ::: ", resultsTitle.getText() + " " + resultsDescription.getText());
                        Toast.makeText(context.getApplicationContext(), "Added " + resultsTitle.getText() + " to itinerary.",  Toast.LENGTH_SHORT).show();
                        ItineraryResults.get(adapterPosition);
                reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
                    child("Does" + doesNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("itineraryTitle").setValue(resultsTitle.getText().toString());
                        dataSnapshot.getRef().child("itineraryDescription").setValue(resultsDescription.getText().toString());
                        dataSnapshot.getRef().child("itineraryDate").setValue(resultsDate.getText().toString());
                        dataSnapshot.getRef().child("itineraryKey").setValue(resultKey);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context.getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }

//TextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickListener.iconTextViewOnClick(v, getAdapterPosition());
//            }
//        });

//        @Override
//        public void onClick(final View view) {
//            Log.i("CLick", "LCICKCKL");
//            // insert data to database
//            reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
//                    child("Does" + doesNum);
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    dataSnapshot.getRef().child("itineraryTitle").setValue(resultsTitle.getText().toString());
//                    dataSnapshot.getRef().child("itineraryDescription").setValue(resultsDescription.getText().toString());
//                    dataSnapshot.getRef().child("itineraryDate").setValue(resultsDate.getText().toString());
//                    dataSnapshot.getRef().child("itineraryKey").setValue(resultKey);
//                    addButton.setText("Clicked");
//
//
//
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(context.getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            });
//
//        }

    }


}


//// NOTES
//
////    Item removal on lists
//    int position = getAdapterPosition();
//    ItineraryResults.remove(position);
//    notifyItemRemoved(position);
//    notifyItemRangeChanged(position, ItineraryResults.size());
