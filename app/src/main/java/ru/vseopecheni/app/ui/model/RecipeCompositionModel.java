package ru.vseopecheni.app.ui.model;

import com.google.gson.annotations.SerializedName;

public class RecipeCompositionModel {

    @SerializedName("MIGX_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
