package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class ItinerarySearch extends AppCompatActivity {

    TextView titlepage, addtitle, adddesc, adddate, itineraryDate;
    EditText itineraryActivity, itineraryDescription;
    Button btnSaveTask, btnCancel, itineraryCalender, btnSearch;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String itineraryKey = Integer.toString(doesNum);
    PlacesClient placesClient;


    final Calendar myCalendar = Calendar.getInstance();

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        itineraryDate.setText(sdf.format(myCalendar.getTime()));
    };

String placeGet = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        itineraryActivity = findViewById(R.id.itinerary_activity);
//        itineraryDescription = findViewById(R.id.itinerary_description);
        itineraryDate = findViewById(R.id.itinerary_date);
        itineraryCalender = findViewById(R.id.itinerary_calender);

//        btnSaveTask = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnSearch = findViewById(R.id.btnSearch);

        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        itineraryCalender.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            new DatePickerDialog(ItinerarySearch.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        final String apiKey = "AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ";
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        //////////////// AUTOCOMPLETE /////////////////////
        // Create a new Places client instance.
        placesClient = Places.createClient(this);
//        final String[] placeId = {""};

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG, Place.Field.RATING
        ));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.

                String city = place.getAddress();
                String placeAddressComponents = place.getAddressComponents().toString();
                Log.i("CITY ::: ", city);

                Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT).show();
                placeGet = city;


//                String placeDetails = "https://maps.googleapis.com/maps/api/place/details/json?place_id=placeID&fields=name,rating,formatted_phone_number&key="+apiKey;
                Log.i("place details", placeAddressComponents);
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        ////////// BUTTON FUNCTIONS //////////////
//        btnSaveTask.setOnClickListener(v -> {
//            // insert data to database
//            reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
//                    child("Does" + doesNum);
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    dataSnapshot.getRef().child("itineraryTitle").setValue(itineraryTitle.getText().toString());
//                    dataSnapshot.getRef().child("itineraryDescription").setValue(itineraryDescription.getText().toString());
//                    dataSnapshot.getRef().child("itineraryDate").setValue(itineraryDate.getText().toString());
//                    dataSnapshot.getRef().child("itineraryKey").setValue(itineraryKey);
//                    Intent a = new Intent(ItinerarySearch.this, MainActivity.class);
//                    startActivity(a);
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        });


        btnSearch.setOnClickListener(v -> {
            Intent a = new Intent(ItinerarySearch.this, ItineraryResults.class);
            a.putExtra("city", placeGet);
            a.putExtra("activity", itineraryActivity.getText().toString());
            startActivity(a);

        });

        btnCancel.setOnClickListener(v -> finish());



    }

}
