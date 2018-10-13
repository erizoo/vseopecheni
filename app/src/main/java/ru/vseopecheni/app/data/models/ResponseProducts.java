package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseProducts {

    @SerializedName("id")
    private String id;
    @SerializedName("tv.app_mozhno")
    private String yes;
    @SerializedName("tv.app_nelzya")
    private String no;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
