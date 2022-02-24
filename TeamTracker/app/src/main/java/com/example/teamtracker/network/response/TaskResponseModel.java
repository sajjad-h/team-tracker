package com.example.teamtracker.network.response;

import com.example.teamtracker.models.Task;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class TaskResponseModel {
    @SerializedName(value = "id")
    public String id;

    @SerializedName(value = "startTime")
    private Long startTime;

    @SerializedName(value = "description")
    private String description;

    @SerializedName(value = "duration")
    private Long duration;

    @SerializedName(value = "title")
    private String title;

    @SerializedName(value = "projectId")
    private String projectId;

    public TaskResponseModel(String id, Long startTime, String description, Long duration, String title, String projectId) {
        this.id = id;
        this.startTime = startTime;
        this.description = description;
        this.duration = duration;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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

    public Task toTaskModel() {
        Task task = new Task(this.title, this.description, this.startTime, this.duration, this.projectId);
        task.setId(UUID.fromString(this.id));
        return task;
    }
}
