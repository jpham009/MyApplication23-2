package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    TextView resultstitlepage;
    ArrayList<ItineraryResult> ItineraryResults;
    ResultsAdapter resultsAdapter;
    RecyclerView itineraryResults;
    ProgressDialog loadingDialog;
    String date = "";

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

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ItineraryResults = new ArrayList<ItineraryResult>();
            resultsAdapter = new ResultsAdapter(ItineraryResults.this, ItineraryResults);
            itineraryResults.setAdapter(resultsAdapter);

            try {

                JSONObject json = new JSONObject(s);
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject nameObject = results.getJSONObject(i);
                    String name = "";
                    String rating = "";
                    String price = "N/A";
                    String placeId = "";

                    name = nameObject.getString("name");
                    if (nameObject.has("rating")) {
                        rating = nameObject.getString("rating");
                    }
                    if (nameObject.has("price_level")) {
                        price = nameObject.getString("price_level");
                    }
                    if (nameObject.has("place_id")) {
                        placeId = nameObject.getString("place_id");
                    }
//                    if (nameObject.has("place_id")) {
////                        price = nameObject.getString("price_level");
//                        Log.i("PHOTO: ",  String.valueOf(i));
//                    }

                    //add to list
                    ItineraryResult p = new ItineraryResult(name, rating, price, "0", date, placeId);
                    ItineraryResults.add(p);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultsAdapter.notifyDataSetChanged();
            loadingDialog.dismiss();
        }
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_results);

            resultstitlepage = findViewById(R.id.titlepage);
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage("Loading..");
            loadingDialog.setTitle("Executing Query");
            loadingDialog.setIndeterminate(false);
            loadingDialog.setCancelable(true);
            loadingDialog.show();


            //setup the recycler view for results.
            itineraryResults = findViewById(R.id.itineraryResults);
            itineraryResults.setLayoutManager(new LinearLayoutManager(this));

            String city = "";
            String activity = "";


            if (getIntent().getExtras() != null) {
                city = (String) getIntent().getSerializableExtra("city");
                activity = (String) getIntent().getSerializableExtra("activity");
                date = (String) getIntent().getSerializableExtra("date");
            }

            city = city.replaceAll(",", "");

            String query = activity + " in " + city;
            query = query.replaceAll(" ", "+");

            DownloadTask task = new DownloadTask();
            task.execute("https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ");

        }

}
