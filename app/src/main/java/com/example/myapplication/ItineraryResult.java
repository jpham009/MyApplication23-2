package com.example.myapplication;

public class ItineraryResult {

    String resultActivity;
    String resultRating;
    String resultPrice;
    String resultKey;
    String resultDate;
    String resultPhotoRef;

    public ItineraryResult(){

    }

    public ItineraryResult(String resultActivity, String resultRating, String resultPrice, String resultKey, String resultDate, String resultPhotoRef) {
        this.resultActivity = resultActivity;
        this.resultRating = resultRating;
        this.resultPrice = resultPrice;
        this.resultKey = resultKey;
        this.resultDate = resultDate;
        this.resultPhotoRef = resultPhotoRef;
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
        switch(resultPrice) {
            case "1":
                return "$";
            case "2":
                return "$$";
            case "3":
                return "$$$";
            default:
                return "n/a";
        }
    }

    public String getResultDate() {
        return resultDate;
    }

    public String getResultPhotoRef() {
        return resultPhotoRef;
    }

}
