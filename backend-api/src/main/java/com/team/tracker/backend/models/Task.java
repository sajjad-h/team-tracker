package com.team.tracker.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "task_id", unique = true)
    private String id;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "title")
    private String title;
    
    @Column(name = "project_id")
    public String projectId;

    public Task() {
    }

    public Task(String id, Long startTime, String description, Long duration, String title, String projectId) {
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
}
