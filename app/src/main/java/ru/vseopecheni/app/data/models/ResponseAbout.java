package ru.vseopecheni.app.data.models;

import com.google.gson.annotations.SerializedName;

public class ResponseAbout {

    @SerializedName("id")
    private String id;
    @SerializedName("pagetitle")
    private String title;
    @SerializedName("tv.image")
    private String img;
    @SerializedName("content")
    private String content;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
