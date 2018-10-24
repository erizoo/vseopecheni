package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMenuForWeek {

    @SerializedName("monday")
    private ResponseType monday;
    @SerializedName("tuesday")
    private ResponseType tuesday;
    @SerializedName("wednesday")
    private ResponseType wednesday;
    @SerializedName("thursday")
    private ResponseType thursday;
    @SerializedName("friday")
    private ResponseType friday;
    @SerializedName("saturday")
    private ResponseType saturday;
    @SerializedName("sunday")
    private ResponseType sunday;

    public ResponseType getMonday() {
        return monday;
    }

    public void setMonday(ResponseType monday) {
        this.monday = monday;
    }

    public ResponseType getTuesday() {
        return tuesday;
    }

    public void setTuesday(ResponseType tuesday) {
        this.tuesday = tuesday;
    }

    public ResponseType getWednesday() {
        return wednesday;
    }

    public void setWednesday(ResponseType wednesday) {
        this.wednesday = wednesday;
    }

    public ResponseType getThursday() {
        return thursday;
    }

    public void setThursday(ResponseType thursday) {
        this.thursday = thursday;
    }

    public ResponseType getFriday() {
        return friday;
    }

    public void setFriday(ResponseType friday) {
        this.friday = friday;
    }

    public ResponseType getSaturday() {
        return saturday;
    }

    public void setSaturday(ResponseType saturday) {
        this.saturday = saturday;
    }

    public ResponseType getSunday() {
        return sunday;
    }

    public void setSunday(ResponseType sunday) {
        this.sunday = sunday;
    }
}
