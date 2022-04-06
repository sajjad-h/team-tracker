package com.example.teamtracker.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProjectsByOwnerResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<ProjectResponseModel> projectList;

    public GetProjectsByOwnerResponseModel(String status, List<ProjectResponseModel> taskList) {
        this.status = status;
        this.projectList = taskList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProjectResponseModel> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectResponseModel> projectList) {
        this.projectList = projectList;
    }
}
