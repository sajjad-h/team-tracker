package com.example.teamtracker.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.teamtracker.models.Task;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Query("SELECT * FROM tasks ORDER BY task_id DESC")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM tasks WHERE project_id = :projectId ORDER BY task_id  DESC")
    LiveData<List<Task>> findTaskByProjectId(int projectId);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
