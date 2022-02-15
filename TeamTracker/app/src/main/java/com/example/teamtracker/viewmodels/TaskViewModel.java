package com.example.teamtracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.teamtracker.models.Task;
import com.example.teamtracker.repositories.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    public void saveTask(Task task) {
        taskRepository.insert(task);
        taskRepository.postTask(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void delete(Task task) {
        taskRepository.delete(task);
    }

    public LiveData<List<Task>> getAllTasksByProjectId(int projectId) {
        return taskRepository.getAllTasksByProjectId(projectId);
    }
}
