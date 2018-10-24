package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseType {

    @SerializedName("breakfast")
    private ResponseMenuFull breakfast;
    @SerializedName("tiffin")
    private ResponseMenuFull tiffin;
    @SerializedName("lunch")
    private ResponseMenuFull lunch;
    @SerializedName("afternoon")
    private ResponseMenuFull afternoon;
    @SerializedName("dinner")
    private ResponseMenuFull dinner;

    public ResponseMenuFull getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ResponseMenuFull breakfast) {
        this.breakfast = breakfast;
    }

    public ResponseMenuFull getTiffin() {
        return tiffin;
    }

    public void setTiffin(ResponseMenuFull tiffin) {
        this.tiffin = tiffin;
    }

    public ResponseMenuFull getLunch() {
        return lunch;
    }

    public void setLunch(ResponseMenuFull lunch) {
        this.lunch = lunch;
    }

    public ResponseMenuFull getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(ResponseMenuFull afternoon) {
        this.afternoon = afternoon;
    }

    public ResponseMenuFull getDinner() {
        return dinner;
    }

    public void setDinner(ResponseMenuFull dinner) {
        this.dinner = dinner;
    }
}
