package com.example.teamtracker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "projects")
public class Project {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "project_id")
    private UUID id = UUID.randomUUID();

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "owner")
    private String owner;

    public Project(String title, String owner) {
        this.title = title;
        this.owner = owner;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
