package com.example.teamtracker.sync;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.ProjectDao;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.response.GetProjectsByOwnerResponseModel;
import com.example.teamtracker.network.response.ProjectResponseModel;
import com.example.teamtracker.network.service.ProjectService;
import com.example.teamtracker.util.SharedRefs;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectSync {
    private ProjectDao projectDao;
    private ProjectService projectService;
    private final SharedRefs sharedRefs;

    public ProjectSync(@NonNull Context context) {
        projectService = APIClient.getInstance().create(ProjectService.class);
        sharedRefs = new SharedRefs(context);
        projectDao = RoomDB.getInstance(context).projectDao();
    }

    public void sync() {
        String owner = sharedRefs.getString(SharedRefs.USER_ID, "-1");
        List<Project> localProjects = projectDao.findProjectByOwner(owner);
        projectService.getProjectByOwner(owner, sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<GetProjectsByOwnerResponseModel>() {
            @Override
            public void onResponse(Call<GetProjectsByOwnerResponseModel> call, Response<GetProjectsByOwnerResponseModel> response) {
                System.out.println(response);
                if (response.code() == 200) {
                    GetProjectsByOwnerResponseModel getProjectsByOwnerResponseModel = response.body();
                    List<ProjectResponseModel> projectResponseModels =  getProjectsByOwnerResponseModel.getProjectList();
                    List<Project> serverProjects = new ArrayList<>();
                    for (ProjectResponseModel projectResponseModel : projectResponseModels) {
                        serverProjects.add(projectResponseModel.toProjectModel());
                    }

                    List<Project> notInLocalProjects = new ArrayList<>(serverProjects);
                    notInLocalProjects.removeAll(localProjects);
                    for (Project project : notInLocalProjects) {
                        projectDao.insert(project);
                    }

                    List<Project> notInServerProjects = new ArrayList<>(localProjects);
                    notInServerProjects.removeAll(serverProjects);
                    for (Project project : notInServerProjects) {
                        projectService.postProject(project.toProjectPostRequestModel(), sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<Response<ResponseBody>>() {
                            @Override
                            public void onResponse(Call<Response<ResponseBody>> call, Response<Response<ResponseBody>> response) {
                                if (response.isSuccessful()) {

                                }
                            }

                            @Override
                            public void onFailure(Call<Response<ResponseBody>> call, Throwable t) {

                            }
                        });
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<GetProjectsByOwnerResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
