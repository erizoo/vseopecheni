package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFullRecipesForMenu {

    @SerializedName("id")
    private String id;
    @SerializedName("pagetitle")
    private String title;
    @SerializedName("tv.image")
    private String image;
    @SerializedName("content")
    private String content;
    @SerializedName("tv.sostav")
    private List<ResponseMenuSostav> sostav;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<ResponseMenuSostav> getSostav() {
        return sostav;
    }

    public void setSostav(List<ResponseMenuSostav> sostav) {
        this.sostav = sostav;
    }
}
