package com.example.myapplication;

public class ItineraryTask {

    String itineraryActivity;
    String itineraryRating;
    String itineraryPrice;
    String itineraryKey;
    String itineraryDate;
    String itineraryPhotoRef;
    String itineraryMonth;
    String itineraryDay;
    String itineraryYear;

    public ItineraryTask(){

    }

    public ItineraryTask(String itineraryActivity, String itineraryRating, String itineraryPrice, String itineraryKey, String itineraryDate, String itineraryPhotoRef) {
        this.itineraryActivity = itineraryActivity;
        this.itineraryRating = itineraryRating;
        this.itineraryPrice = itineraryPrice;
        this.itineraryKey = itineraryKey;
        this.itineraryDate = itineraryDate;
        this.itineraryPhotoRef = itineraryPhotoRef;
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

    public String getItineraryPhotoRef() { return itineraryPhotoRef; }
//
//    public String getItineraryYear() { if (itineraryDate != "") return itineraryDate.substring(0, 4); else return " ";}
//
//    public String getItineraryMonth() { if (itineraryDate != "" || itineraryDate != null) return itineraryDate.substring(5, 7); else return " ";}
//
//    public String getItineraryDay() { if (itineraryDate != "") return itineraryDate.substring(8, 10); else return " ";}


}
