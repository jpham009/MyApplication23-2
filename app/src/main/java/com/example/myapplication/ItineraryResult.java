package com.example.myapplication;

public class ItineraryResult {

    String resultActivity;
    String resultRating;
    String resultPrice;
    String resultKey;
    String resultDate;

    public ItineraryResult(){

    }

    public ItineraryResult(String resultActivity, String resultRating, String resultPrice, String resultKey, String resultDate) {
        this.resultActivity = resultActivity;
        this.resultRating = resultRating;
        this.resultPrice = resultPrice;
        this.resultKey = resultKey;
        this.resultDate = resultDate;
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

}
