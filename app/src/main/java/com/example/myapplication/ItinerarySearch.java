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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class ItinerarySearch extends AppCompatActivity {

    TextView titlepage, addtitle, adddesc, adddate, itineraryDate;
    EditText itineraryTitle, itineraryDescription;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        itineraryTitle = findViewById(R.id.itinerary_title);
        itineraryDescription = findViewById(R.id.itinerary_description);
        itineraryDate = findViewById(R.id.itinerary_date);
        itineraryCalender = findViewById(R.id.itinerary_calender);

        btnSaveTask = findViewById(R.id.btnSave);
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

        btnSaveTask.setOnClickListener(v -> {
            // insert data to database
            reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
                    child("Does" + doesNum);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("itineraryTitle").setValue(itineraryTitle.getText().toString());
                    dataSnapshot.getRef().child("itineraryDescription").setValue(itineraryDescription.getText().toString());
                    dataSnapshot.getRef().child("itineraryDate").setValue(itineraryDate.getText().toString());
                    dataSnapshot.getRef().child("itineraryKey").setValue(itineraryKey);

                    Intent a = new Intent(ItinerarySearch.this, MainActivity.class);
                    startActivity(a);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();

                }
            });

        });

        btnSearch.setOnClickListener(v -> {
            Intent a = new Intent(ItinerarySearch.this, ItineraryResults.class);
            startActivity(a);
        });

        btnCancel.setOnClickListener(v -> finish());


        final String apiKey = "AIzaSyD37Ltc_DFCSVzpDxJHYfyuC2_doVmcbeQ";
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
//                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), place.getId(), Toast.LENGTH_SHORT).show();
//                Log.i("place", String.valueOf(place));
                String placeID = place.getId();
//                String placeDetails = "https://maps.googleapis.com/maps/api/place/details/json?place_id=placeID&fields=name,rating,formatted_phone_number&key="+apiKey;
                Log.i("place details", String.valueOf(place));


                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.getPhotoMetadatas()).get(0))
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                        new OnSuccessListener<FetchPhotoResponse>() {
                            @Override
                            public void onSuccess(FetchPhotoResponse response) {
//                                Bitmap bitmap = response.getBitmap();
//                                ((ImageView)findViewById(R.id.img)).setImageBitmap(bitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                exception.printStackTrace();
                            }
                        });
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
