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

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    List<Task> getAll();

    @Query("UPDATE tasks SET title = :title, description = :description WHERE id = :id")
    void update(int id, String title, String description);

    @Delete
    void delete(Task task);
}
