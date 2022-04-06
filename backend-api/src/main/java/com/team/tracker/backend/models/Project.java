package com.team.tracker.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Column;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "project_id", unique = true)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "owner")
    private String owner;

    public Project() {
    }
    
    public Project(String id, String title, String owner) {
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
}
