package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMenuFull {

    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private ResponseFullRecipes content;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResponseFullRecipes getContent() {
        return content;
    }

    public void setContent(ResponseFullRecipes content) {
        this.content = content;
    }
}
