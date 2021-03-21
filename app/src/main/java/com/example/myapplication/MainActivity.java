package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView titlepage, endpage;
    FloatingActionButton btnAddNew;
    DatabaseReference reference;
    RecyclerView itineraryTasks;
    ArrayList<ItineraryTask> ItineraryTasks;
    ItineraryAdapter itineraryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        titlepage = findViewById(R.id.titlepage);
        endpage = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);


        btnAddNew.setOnClickListener(v -> {
            Intent a = new Intent(MainActivity.this, ItinerarySearch.class);
            startActivity(a);
        });




        // working with data
        itineraryTasks = findViewById(R.id.itineraryTasks);
        itineraryTasks.setLayoutManager(new LinearLayoutManager(this));
        ItineraryTasks = new ArrayList<ItineraryTask>();
        itineraryAdapter = new ItineraryAdapter(MainActivity.this, ItineraryTasks);
        itineraryTasks.setAdapter(itineraryAdapter);
//
        // get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Itinerary");
        Query query = reference.orderByChild("itineraryKey");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ItineraryTask p = dataSnapshot1.getValue(ItineraryTask.class);
                    ItineraryTasks.add(p);

                }
                itineraryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}