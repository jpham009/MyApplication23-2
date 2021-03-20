package com.example.myapplication;

public class ItineraryResult {

    String resultTitle;
    String resultDate;
    String resultDescription;
    String resultKey;

    public ItineraryResult(){

    }

    public ItineraryResult(String resultTitle, String resultDate, String resultDescription, String resultKey) {
        this.resultTitle = resultTitle;
        this.resultDate = resultDate;
        this.resultDescription = resultDescription;
        this.resultKey = resultKey;
    }

    public String getResultKey() {
        return resultKey;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public String getResultDate() {
        return resultDate;
    }

    public String getResultDescription() {
        return resultDescription;
    }

}
