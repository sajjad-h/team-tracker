package com.example.teamtracker.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.ProjectDao;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.util.SharedRefs;

import java.util.List;

public class ProjectRepository {
    private ProjectDao projectDao;
    SharedRefs sharedRefs;

    public ProjectRepository(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        projectDao = database.projectDao();
        sharedRefs = new SharedRefs(application);
    }

    public void insert(Project project) {
        projectDao.insert(project);
    }

    public void delete(Project project) {
        projectDao.delete(project);
    }

    public LiveData<List<Project>> getAll() {
        return projectDao.getAll();
    }
}
