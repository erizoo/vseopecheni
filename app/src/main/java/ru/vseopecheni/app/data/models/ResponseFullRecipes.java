package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseFullRecipes {

    @SerializedName("pagetitle")
    private String title;
    @SerializedName("tv.image")
    private String image;
    @SerializedName("content")
    private String content;
    @SerializedName("tv.sostav")
    private String sostav;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSostav() {
        return sostav;
    }

    public void setSostav(String sostav) {
        this.sostav = sostav;
    }
}
