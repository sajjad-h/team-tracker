package com.example.teamtracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.repositories.ProjectRepository;
import com.example.teamtracker.repositories.TaskRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository projectRepository;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
    }

    public void saveProject(Project project) {
        projectRepository.insert(project);
        projectRepository.postProject(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectRepository.getAll();
    }
}
