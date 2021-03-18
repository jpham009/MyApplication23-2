package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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


            //Delete me
            Log.i("ALL JSON:::: ", s);


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
                    if (nameObject.has("rating")) {
                        rating = nameObject.getString("rating");
                    }
                    if (nameObject.has("price_level")) {
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

            resultstitlepage = findViewById(R.id.titlepage);
            resultssubtitlepage = findViewById(R.id.resultssubtitlepage);
//        resultsendpage = findViewById(R.id.resultsendpage);
//            resultsTest = findViewById(R.id.results_text_test);


            //setup the recycler view for results.
            itineraryResults = findViewById(R.id.itineraryResults);
            itineraryResults.setLayoutManager(new LinearLayoutManager(this));

            String city = "";
            String activity = "";

            if (getIntent().getExtras() != null) {
                city = (String) getIntent().getSerializableExtra("city");
                activity = (String) getIntent().getSerializableExtra("activity");
            }
//        resultsTest.setText(placeId);

            city = city.replaceAll(",", "");


            Log.i("The city is::::::::", city);

            int radius = 500;
            String query = activity + " in " + city;
            query = query.replaceAll(" ", "+");
            Log.i("QUERY::::::", query);

//        String fields = "&fields=formatted_address,icon,name,photos,type,url,price";
            DownloadTask task = new DownloadTask();
            task.execute("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ");


            //getting photos from reference
          //  https://maps.googleapis.com/maps/api/place/photo?photoreference=PHOTO_REFERENCE&sensor=false&maxheight=MAX_HEIGHT&maxwidth=MAX_WIDTH&key=YOUR_API_KEY
            //example photo reference
            //ATtYBwJn6VKTA7jzhq46BUVU5H-Z5cfD8X7m_fIW5ihE8u7VuJ7ml3Lblegg6ax7dU-JJQzgtVEvhMIHQ81jS2dzxRnC6-PtJDbLW3TczqZu_rxY8h7PKcgS9wh5QBR0LRumIm-Bioc9GpsQ8zpY21kwQa8wOjHZAd5n-yUgk3LFgWBS7b0J

            //test photo api call
            //  https://maps.googleapis.com/maps/api/place/photo?photoreference=ATtYBwL4tYveRA8WDMoR&sensor=false&maxheight=MAX_HEIGHT&maxwidth=MAX_WIDTH&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ

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
