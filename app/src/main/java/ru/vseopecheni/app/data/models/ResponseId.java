package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseId {

    @SerializedName("id")
    private String id;

    public ResponseId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
