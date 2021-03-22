package com.example.myapplication;

public class ItineraryResult {

    String resultActivity;
    String resultRating;
    String resultPrice;
    String resultKey;
    String resultDate;
    String resultPlaceId;

    public ItineraryResult(){

    }

    public ItineraryResult(String resultActivity, String resultRating, String resultPrice, String resultKey, String resultDate, String resultPlaceId) {
        this.resultActivity = resultActivity;
        this.resultRating = resultRating;
        this.resultPrice = resultPrice;
        this.resultKey = resultKey;
        this.resultDate = resultDate;
        this.resultPlaceId = resultPlaceId;
    }

    public String getResultKey() {
        return resultKey;
    }

    public String getResultActivity() {
        return resultActivity;
    }

    public String getResultRating() {
        return resultRating;
    }

    public String getResultPrice() {
        return resultPrice;
    }

    public String getResultDate() {
        return resultDate;
    }

    public String getResultPlaceId() {
        return resultPlaceId;
    }

}
