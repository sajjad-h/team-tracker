package com.example.teamtracker.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTaskByProjectIdResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<TaskResponseModel> taskList;

    public GetTaskByProjectIdResponseModel(String status, List<TaskResponseModel> taskList) {
        this.status = status;
        this.taskList = taskList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TaskResponseModel> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskResponseModel> taskList) {
        this.taskList = taskList;
    }
}
