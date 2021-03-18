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
        myViewHolder.resultsTitle.setText(ItineraryResults.get(i).getResultTitle());
        myViewHolder.resultsDescription.setText(ItineraryResults.get(i).getResultDescription());
        myViewHolder.resultsDate.setText(ItineraryResults.get(i).getResultDate());

        final String getResultTitle = ItineraryResults.get(i).getResultTitle();
        final String getResultDescription = ItineraryResults.get(i).getResultDescription();
        final String getResultDate = ItineraryResults.get(i).getResultDate();
        final String getResultKey = ItineraryResults.get(i).getResultKey();

        myViewHolder.itemView.setOnClickListener(v -> {
            Intent aa = new Intent(context, ResultsAdapter.class);
            aa.putExtra("resultsTitle", getResultTitle);
            aa.putExtra("resultsDescription", getResultDescription);
            aa.putExtra("resultsDate", getResultDate);
            aa.putExtra("resultsKey", getResultKey);
            context.startActivity(aa);
        });
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
        }
    }


}
