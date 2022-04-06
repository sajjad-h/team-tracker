package com.example.teamtracker.network.request;

import com.google.gson.annotations.SerializedName;

public class ProjectPostRequestModel {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("owner")
    private String owner;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ProjectPostRequestModel(String id, String title, String owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
    }
}
