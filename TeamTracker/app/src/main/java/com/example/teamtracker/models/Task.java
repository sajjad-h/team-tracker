package com.example.teamtracker.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTaskTitle() {
        return title;
    }

    public String getTaskDescription() {
        return description;
    }

    public void setTaskTitle(String title) {
        this.title = title;
    }

    public void setTaskDescription(String description) {
        this.description = description;
    }
}
