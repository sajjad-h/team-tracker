package com.example.teamtracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.teamtracker.models.Task;
import com.example.teamtracker.repositories.TaskRepository;

import java.util.List;

public class GraphViewModel extends AndroidViewModel {
    private TaskRepository repository;

    public GraphViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
    }

    public void insert(Task task) {
        repository.insert(task);
    }

    public void update(Task task) {
        repository.update(task);
    }

    public void delete(Task task) {
        repository.delete(task);
    }

    public LiveData<List<Task>> getAllTasksByProjectId(int projectId) {
        return repository.getAllTasksByProjectId(projectId);
    }
}
