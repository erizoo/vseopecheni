package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseMenuFull {

    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private ResponseFullRecipesForMenu content;

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

    public ResponseFullRecipesForMenu getContent() {
        return content;
    }

    public void setContent(ResponseFullRecipesForMenu content) {
        this.content = content;
    }
}
