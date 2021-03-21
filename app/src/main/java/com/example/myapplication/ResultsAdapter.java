package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ItineraryResult> ItineraryResults;

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
        myViewHolder.resultsActivity.setText(ItineraryResults.get(i).getResultActivity());
        myViewHolder.resultsPrice.setText(ItineraryResults.get(i).getResultPrice());
        myViewHolder.resultsRating.setText(ItineraryResults.get(i).getResultRating());
        myViewHolder.resultsDate.setText(ItineraryResults.get(i).getResultDate());
        myViewHolder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return ItineraryResults.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView resultsActivity;
        TextView resultsPrice;
        TextView resultsRating;
        TextView resultsDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsActivity = itemView.findViewById(R.id.result_title);
            resultsPrice = itemView.findViewById(R.id.result_price);
            resultsRating = itemView.findViewById(R.id.result_rating);
            resultsDate = itemView.findViewById(R.id.result_date);
            Button addButton = itemView.findViewById(R.id.add_button);
            addButton.setOnClickListener(v -> {

                int adapterPosition = getAdapterPosition();
//                Log.i("ADAPTER POSITION" , String.valueOf(adapterPosition));
//                Log.i("PLACE ::: ", resultsActivity.getText() + " " + resultsPrice.getText());
                Toast.makeText(context.getApplicationContext(), "Added " + resultsActivity.getText() + " to itinerary.",  Toast.LENGTH_SHORT).show();
                ItineraryResults.get(adapterPosition);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Itinerary").push();
                Map<String, Object> map = new HashMap<>();
                map.put("itineraryKey", databaseReference.getKey());
                map.put("itineraryActivity", resultsActivity.getText().toString());
                map.put("itineraryPrice", resultsPrice.getText().toString());
                map.put("itineraryRating", resultsRating.getText().toString());
                map.put("itineraryDate", resultsDate.getText().toString());

                databaseReference.setValue(map);

                addButton.setEnabled(false);

            });
        }
    }
}



