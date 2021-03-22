package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.URL;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.resultsActivity.setText(ItineraryResults.get(i).getResultActivity());
        myViewHolder.resultsPrice.setText("Price: " + ItineraryResults.get(i).getResultPrice());
        myViewHolder.resultsRating.setText("Rating: " + ItineraryResults.get(i).getResultRating());
        myViewHolder.resultsDate.setText(ItineraryResults.get(i).getResultDate());
        try{
            myViewHolder.resultsRatingBar.setRating( Float.parseFloat(ItineraryResults.get(i).getResultRating()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
        RatingBar resultsRatingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsActivity = itemView.findViewById(R.id.result_title);
            resultsPrice = itemView.findViewById(R.id.result_price);
            resultsRating = itemView.findViewById(R.id.result_rating);
            resultsDate = itemView.findViewById(R.id.result_date);
            resultsRatingBar = itemView.findViewById(R.id.result_ratingbar);
            Button addButton = itemView.findViewById(R.id.add_button);
            addButton.setOnClickListener(v -> {

                int adapterPosition = getAdapterPosition();
                Toast.makeText(context.getApplicationContext(), "Added " + resultsActivity.getText() + " to itinerary.",  Toast.LENGTH_SHORT).show();
                ItineraryResults.get(adapterPosition);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Itinerary").push();
                Map<String, Object> map = new HashMap<>();
                map.put("itineraryKey", databaseReference.getKey());
                map.put("itineraryActivity", ItineraryResults.get(adapterPosition).getResultActivity());
                map.put("itineraryPrice", ItineraryResults.get(adapterPosition).getResultPrice());
                map.put("itineraryRating", ItineraryResults.get(adapterPosition).getResultRating());
                map.put("itineraryDate", ItineraryResults.get(adapterPosition).getResultDate());

                databaseReference.setValue(map);

                addButton.setEnabled(false);

            });
        }

    }

}



