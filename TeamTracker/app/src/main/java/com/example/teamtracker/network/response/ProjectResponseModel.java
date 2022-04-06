package com.example.teamtracker.network.response;

import com.example.teamtracker.models.Project;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class ProjectResponseModel {
    @SerializedName(value = "id")
    public String id;

    @SerializedName(value = "title")
    private String title;

    @SerializedName(value = "owner")
    private String owner;

    public ProjectResponseModel(String id, String title, String owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
    }

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

    public Project toProjectModel() {
        Project project = new Project(this.title, this.owner);
        project.setId(UUID.fromString(this.id));
        return project;
    }
}
