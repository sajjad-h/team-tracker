package com.example.teamtracker.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.teamtracker.network.request.TaskPostRequestModel;
import com.example.teamtracker.network.response.TaskResponseModel;

import java.util.UUID;

@Entity(tableName = "tasks")
public class Task {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "task_id")
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "start_time")
    private Long startTime;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "duration")
    private Long duration;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "project_id")
    public String projectId;

    public Task(String title, String description, Long startTime, Long duration, String projectId) {
        this.title = title;
        this.startTime = startTime;
        this.duration = duration;
        this.description = description;
        this.projectId = projectId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public TaskPostRequestModel toTaskPostRequestModel() {
        return new TaskPostRequestModel(this.id.toString(), this.startTime, this.duration, this.description, this.title, this.projectId);
    }

    public String toString() {
        return "Task { " +
                    "id: " + this.getId().toString() + ", " +
                    "startTime: " + this.startTime + ", " +
                    "duration: " + this.duration + ", " +
                    "title: " + this.title + ", " +
                    "description: " + this.description + ", " +
                    "projectId: " + this.projectId +
                " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Task taskOther = (Task) obj;
        return  taskOther.getId().equals(this.getId()) &&
                taskOther.getTitle().equals(this.getTitle()) &&
                taskOther.getDescription().equals(this.getDescription()) &&
                taskOther.getStartTime().equals(this.getStartTime()) &&
                taskOther.getDuration().equals(this.getDuration()) &&
                taskOther.getProjectId().equals(this.getProjectId());
    }
}
