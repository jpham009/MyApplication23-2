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

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public String getResultTitle() {
        return resultTitle;
    }

    public void setResultTitle(String resultTitle) {
        this.resultTitle = resultTitle;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

}
