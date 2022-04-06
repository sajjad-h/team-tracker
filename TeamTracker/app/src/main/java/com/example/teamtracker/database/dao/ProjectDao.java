package com.example.teamtracker.database.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = REPLACE)
    void insert(Project project);

    @Query("SELECT * FROM projects ORDER BY project_id DESC")
    LiveData<List<Project>> getAll();

    @Query("SELECT * FROM projects WHERE owner = :owner ORDER BY project_id DESC")
    List<Project> findProjectByOwner(String owner);

    @Delete
    void delete(Project note);
}
