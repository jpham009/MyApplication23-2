package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItineraryResults extends AppCompatActivity {
    TextView resultstitlepage, resultssubtitlepage, resultsendpage;
    TextView resultsTest;

    DatabaseReference reference;
    RecyclerView resultsList;
    ArrayList<ItineraryResult> list;
    ItineraryAdapter itineraryAdapter;

    PlacesClient placesClient;


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
        final String apiKey = "AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ";
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getId(), Toast.LENGTH_SHORT).show();
                Log.i("place:::::", String.valueOf(place));


                try {
                    JSONObject jsonObject = new JSONObject((Map) place);
                    String city = jsonObject.getString("weather");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String placeDetails = "https://maps.googleapis.com/maps/api/place/details/json?" + place.getId() + "=placeID&fields=name,rating,formatted_phone_number&key=" + apiKey;
                Log.i("place details", String.valueOf(placeDetails));


                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.getPhotoMetadatas()).get(0))
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                        new OnSuccessListener<FetchPhotoResponse>() {
                            @Override
                            public void onSuccess(FetchPhotoResponse response) {
                                Bitmap bitmap = response.getBitmap();
//                                ((ImageView) findViewById(R.id.img)).setImageBitmap(bitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                exception.printStackTrace();
                            }
                        });
            }

            /*
            dallas tx placeid = ChIJS5dFe_cZTIYRj2dH9qSb7Lk
            https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJS5dFe_cZTIYRj2dH9qSb7Lk&fields=name,lat,long&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ
https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJS5dFe_cZTIYRj2dH9qSb7Lk&fields=geometry/location&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ

            */

            /////////////////////////
            Geocoder mGeocoder;

            private void getPlaceInfo(double lat, double lon) throws IOException {
                List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);
                if (addresses.get(0).getPostalCode() != null) {
                    String ZIP = addresses.get(0).getPostalCode();
                    Log.d("ZIP CODE", ZIP);
                }

                if (addresses.get(0).getLocality() != null) {
                    String city = addresses.get(0).getLocality();
                    Log.d("CITY", city);
                }

                if (addresses.get(0).getAdminArea() != null) {
                    String state = addresses.get(0).getAdminArea();
                    Log.d("STATE", state);
                }

                if (addresses.get(0).getCountryName() != null) {
                    String country = addresses.get(0).getCountryName();
                    Log.d("COUNTRY", country);
                }
            }


            //////////////////////////


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
