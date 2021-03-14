package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItineraryResults extends AppCompatActivity {
    TextView resultstitlepage, resultssubtitlepage, resultsendpage;
    TextView resultsTest;

    DatabaseReference reference;
    RecyclerView resultsList;
    ArrayList<ItineraryResult> list;
    ItineraryAdapter itineraryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultstitlepage = findViewById(R.id.resultstitlepage);
        resultssubtitlepage = findViewById(R.id.resultssubtitlepage);
        resultsendpage = findViewById(R.id.resultsendpage);
        resultsTest = findViewById(R.id.results_text_test);



//        btnAddNew = findViewById(R.id.btnAddNew);
//
//        btnAddNew.setOnClickListener(v -> {
//            Intent a = new Intent(MainActivity.this, ItinerarySearch.class);
//            startActivity(a);
//        });
//
//
        // working with data
        resultsList = findViewById(R.id.resultsList);
//        resultsList.setLayoutManager(new LinearLayoutManager(this));
//        list = new ArrayList<ItineraryResult>();


        //pull data from API


        //put data in list

        //display list

        //add activity function to database.

        //finish button to finish and return to main page

////
//        // get data from firebase
//        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // set code to retrieve data and replace layout
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    ItineraryTask p = dataSnapshot1.getValue(ItineraryTask.class);
//                    list.add(p);
//                }
//                itineraryAdapter = new ItineraryAdapter(MainActivity.this, list);
//                itineraryTasks.setAdapter(itineraryAdapter);
//                itineraryAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // set code to show an error
//                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//            }
//        });
//
    }
}
