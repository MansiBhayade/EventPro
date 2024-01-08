package com.example.eventpro;

import android.net.Uri;

// Created By Mansi Bhayade
public class MainModel {

    String eventname,details,organizers,venue,purl;


    MainModel(){

    }
    public MainModel(String eventname, String details, String organizers, String venue, String purl) {
        this.eventname = eventname;
        this.details = details;
        this.organizers = organizers;
        this.venue = venue;
        this.purl = purl;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOrganizers() {
        return organizers;
    }

    public void setOrganizers(String organizers) {
        this.organizers = organizers;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
