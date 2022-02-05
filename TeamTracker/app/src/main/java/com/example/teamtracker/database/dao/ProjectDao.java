package com.example.teamtracker.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.teamtracker.models.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = REPLACE)
    void insert(Project project);

    @Query("SELECT * FROM projects ORDER BY id DESC")
    List<Project> getAll();

    @Query("UPDATE projects SET name = :name WHERE id = :id")
    void update(int id, String name);

    @Delete
    void delete(Project note);
}
