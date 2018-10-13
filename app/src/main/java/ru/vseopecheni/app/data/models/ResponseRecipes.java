package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseRecipes {

    @SerializedName("id")
    private String id;
    @SerializedName("urlFeed")
    private String url;
    @SerializedName("image")
    private String image;
    @SerializedName("title")
    private String title;

    public ResponseRecipes() {
    }

    public ResponseRecipes(String id, String url, String image, String title) {
        this.id = id;
        this.url = url;
        this.image = image;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
