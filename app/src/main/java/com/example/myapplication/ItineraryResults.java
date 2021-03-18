package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItineraryResults extends AppCompatActivity {

    TextView resultstitlepage, resultssubtitlepage, resultsendpage;
    TextView resultsTest;

    DatabaseReference reference;
    ArrayList<ItineraryResult> resultList;
    ResultsAdapter resultsAdapter;
    RecyclerView itineraryResults;


    PlacesClient placesClient;


    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        //        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            // working with data

            resultList = new ArrayList<ItineraryResult>();


            try {

                //////////////////
                JSONObject json = new JSONObject(s.toString());
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject nameObject = results.getJSONObject(i);
                    String name = "";
                    String rating = "";
                    String price = "";
//                    try {
                    name = nameObject.getString("name");
                    if (nameObject.getString("rating") != null) {
                        rating = nameObject.getString("rating");
                    }
                    if (nameObject.getString("price_level") != null) {
                        price = nameObject.getString("price_level");
                    }
                    Log.i("Name:", name);
                    Log.i("Rating:", rating);
                    Log.i("Price:", price);
                    //add to list
                    ItineraryResult p = new ItineraryResult(name, rating, price, "0");
                    resultList.add(p);
//                    }catch (Exception e) {
//                        e.printStackTrace();
//                    }


                }
//                }
//                JSONObject result = json.getJSONObject("result");
//                JSONArray result2 = result.getJSONArray("address_components");
//                JSONObject result3 = result2.getJSONObject(0);
//                String result4 = result3.getString("long_name");
//
//                Log.i("results::::", result4.toString());
//                Log.i("results length::::", String.valueOf(result2.length()));
//                for (int i = 0; i < result2.length(); i++) {
//                    JSONObject result5 = result2.getJSONObject(i);
//
//                    String result6 = result5.getString("long_name");
//                    Log.i("results::::", result6);
//                    if (i < result2.length() - 1) {
//                        finalString += result6 + ", ";
//                    } else {
//                        finalString += result6;
//                    }
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultsAdapter = new ResultsAdapter(ItineraryResults.this, resultList);
            itineraryResults.setAdapter(resultsAdapter);
            resultsAdapter.notifyDataSetChanged();


//
//            // get data from firebase
//            reference = FirebaseDatabase.getInstance().getReference().child("itineraryResults");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    // set code to retrieve data and replace layout
//                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                    {
//                        ItineraryTask p = dataSnapshot1.getValue(ItineraryTask.class);
//                        list.add(p);
//                    }
//                    itineraryAdapter = new ItineraryAdapter(MainActivity.this, list);
//                    itineraryTasks.setAdapter(itineraryAdapter);
//                    itineraryAdapter.notifyDataSetChanged();
//                }
//        });
        }
    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_results);

            resultstitlepage = findViewById(R.id.resultstitlepage);
            resultssubtitlepage = findViewById(R.id.resultssubtitlepage);
//        resultsendpage = findViewById(R.id.resultsendpage);
            resultsTest = findViewById(R.id.results_text_test);


            //setup the recycler view for results.
            itineraryResults = findViewById(R.id.itineraryResults);
            itineraryResults.setLayoutManager(new LinearLayoutManager(this));

            String city = "";

            if (getIntent().getExtras() != null) {
                city = (String) getIntent().getSerializableExtra("city");
            }
//        resultsTest.setText(placeId);

            Log.i("The city is::::::::", city);

            int radius = 500;
            String query = "restaurant in" + city;
//        String fields = "&fields=formatted_address,icon,name,photos,type,url,price";
            DownloadTask task = new DownloadTask();
            task.execute("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ");

//
            // working with data
//        resultsList = findViewById(R.id.resultsList);
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
////
//        final String apiKey = "AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ";
//        if (!Places.isInitialized()) {
//            Places.initialize(getApplicationContext(), apiKey);
//        }
///////////////////////////////////////////
//            Geocoder mGeocoder;
//
//            private void getPlaceInfo(double lat, double lon) throws IOException {
//                List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);
//                if (addresses.get(0).getPostalCode() != null) {
//                    String ZIP = addresses.get(0).getPostalCode();
//                    Log.d("ZIP CODE", ZIP);
//                }
//
//                if (addresses.get(0).getLocality() != null) {
//                    String city = addresses.get(0).getLocality();
//                    Log.d("CITY", city);
//                }
//
//                if (addresses.get(0).getAdminArea() != null) {
//                    String state = addresses.get(0).getAdminArea();
//                    Log.d("STATE", state);
//                }
//
//                if (addresses.get(0).getCountryName() != null) {
//                    String country = addresses.get(0).getCountryName();
//                    Log.d("COUNTRY", country);
//                }
//            }
//
//
//            //////////////////////////
//
//
//            @Override
//            public void onError(@NonNull Status status) {
//                // TODO: Handle the error.
//                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
        }

}
