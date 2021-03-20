package com.example.myapplication;

public class ItineraryTask {

    String itineraryTitle;
    String itineraryDate;
    String itineraryDescription;
    String itineraryKey;

    public ItineraryTask(){

    }

    public ItineraryTask(String itineraryTitle, String itineraryDate, String itineraryDescription, String itineraryKey) {
        this.itineraryTitle = itineraryTitle;
        this.itineraryDate = itineraryDate;
        this.itineraryDescription = itineraryDescription;
        this.itineraryKey = itineraryKey;
    }

    public String getItineraryKey() {
        return itineraryKey;
    }

    public String getItineraryTitle() {
        return itineraryTitle;
    }

    public String getItineraryDate() {
        return itineraryDate;
    }

    public String getItineraryDescription() { return itineraryDescription; }


}
