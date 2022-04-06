package com.example.teamtracker.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.ProjectDao;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.request.ProjectPostRequestModel;
import com.example.teamtracker.network.service.ProjectService;
import com.example.teamtracker.util.SharedRefs;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private ProjectDao projectDao;
    private ProjectService projectService;
    SharedRefs sharedRefs;

    public ProjectRepository(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        projectDao = database.projectDao();
        sharedRefs = new SharedRefs(application);
        projectService = APIClient.getInstance().create(ProjectService.class);
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

    public void postProject(Project project) {
        projectService.postProject(new ProjectPostRequestModel(project.getId().toString(), project.getTitle(), project.getOwner()), sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<Response<ResponseBody>>() {
            @Override
            public void onResponse(Call<Response<ResponseBody>> call, Response<Response<ResponseBody>> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<Response<ResponseBody>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
