package com.example.teamtracker.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.teamtracker.models.Task;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Query("SELECT * FROM tasks ORDER BY task_id DESC")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE project_id = :projectId ORDER BY task_id  DESC")
    List<Task> findTaskByProjectId(int projectId);

    @Query("UPDATE tasks SET title = :title, description = :description WHERE task_id = :taskId")
    void update(int taskId, String title, String description);

    @Delete
    void delete(Task task);
}
