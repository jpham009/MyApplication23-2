package com.example.myapplication;

public class ItineraryTask {

    String itineraryActivity;
    String itineraryRating;
    String itineraryPrice;
    String itineraryDate;
    String itineraryKey;

    public ItineraryTask(){

    }

    public ItineraryTask(String itineraryActivity, String itineraryRating, String itineraryPrice, String itineraryKey, String itineraryDate) {
        this.itineraryActivity = itineraryActivity;
        this.itineraryRating = itineraryRating;
        this.itineraryPrice = itineraryPrice;
        this.itineraryKey = itineraryKey;
        this.itineraryDate = itineraryDate;
    }

    public String getItineraryKey() {
        return itineraryKey;
    }

    public String getItineraryActivity() {
        return itineraryActivity;
    }

    public String getItineraryRating() {
        return itineraryRating;
    }

    public String getItineraryPrice() { return itineraryPrice; }

    public String getItineraryDate() { return itineraryDate; }


}
