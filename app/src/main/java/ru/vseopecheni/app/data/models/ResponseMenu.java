package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMenu {

    @SerializedName("array")
    private List<ResponseMenuForWeek> array;

    public List<ResponseMenuForWeek> getArray() {
        return array;
    }

    public void setArray(List<ResponseMenuForWeek> array) {
        this.array = array;
    }
}
