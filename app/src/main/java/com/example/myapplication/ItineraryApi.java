package com.example.myapplication;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItineraryApi extends AppCompatActivity {


    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


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
//                String placeDetails = "https://maps.googleapis.com/maps/api/place/details/json?place_id=placeID&fields=name,rating,formatted_phone_number&key="+apiKey;
                Log.i("place details", String.valueOf(place));


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
