package com.example.myapplication;

public class ItineraryResult {

    String resultTitle;
    String resultRating;
    String resultPrice;
    String resultKey;
    String resultDate;

    public ItineraryResult(){

    }

    public ItineraryResult(String resultTitle, String resultRating, String resultPrice, String resultKey, String resultDate) {
        this.resultTitle = resultTitle;
        this.resultRating = resultRating;
        this.resultPrice = resultPrice;
        this.resultKey = resultKey;
        this.resultDate = resultDate;
    }

    public String getResultKey() {
        return resultKey;
    }

    public String getResultTitle() {
        return resultTitle;
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
