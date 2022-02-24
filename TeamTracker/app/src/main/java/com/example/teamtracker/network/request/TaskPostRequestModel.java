package com.example.teamtracker.network.request;

import com.google.gson.annotations.SerializedName;

public class TaskPostRequestModel {

    @SerializedName("id")
    private String id;

    @SerializedName("startTime")
    private Long startTime;

    @SerializedName("duration")
    private Long duration;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("projectId")
    private String projectId;

    public TaskPostRequestModel(String id, Long startTime, Long duration, String description, String title, String projectId) {
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.description = description;
        this.title = title;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


}
