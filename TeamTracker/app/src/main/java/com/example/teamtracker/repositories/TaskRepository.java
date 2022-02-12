package com.example.teamtracker.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.TaskDao;
import com.example.teamtracker.models.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;

    public TaskRepository(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        taskDao = database.taskDao();
    }

    public void insert(Task task) {
        taskDao.insert(task);
    }

    public void update(Task task) {
        taskDao.update(task);
    }

    public void delete(Task task) {
        taskDao.delete(task);
    }

    public LiveData<List<Task>> getAllTasksByProjectId(int projectId) {
        return taskDao.findTaskByProjectId(projectId);
    }

}
