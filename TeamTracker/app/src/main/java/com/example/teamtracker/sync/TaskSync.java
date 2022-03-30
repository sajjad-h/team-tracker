package com.example.teamtracker.sync;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.TaskDao;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.response.GetTaskByProjectIdResponseModel;
import com.example.teamtracker.network.response.TaskResponseModel;
import com.example.teamtracker.network.service.TaskService;
import com.example.teamtracker.util.SharedRefs;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskSync {
    private TaskDao taskDao;
    private TaskService taskService;
    private SharedRefs sharedRefs;

    public TaskSync(@NonNull Context context) {
        taskService = APIClient.getInstance().create(TaskService.class);
        sharedRefs = new SharedRefs(context);
        taskDao = RoomDB.getInstance(context).taskDao();
    }

    public void sync(String projectId) {
        List<Task> localTasks = taskDao.findTaskByProjectId(projectId);
        taskService.getTaskByProjectId(projectId, sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<GetTaskByProjectIdResponseModel>() {
            @Override
            public void onResponse(Call<GetTaskByProjectIdResponseModel> call, Response<GetTaskByProjectIdResponseModel> response) {
                System.out.println(response);
                if (response.code() == 200) {
                    GetTaskByProjectIdResponseModel getTaskByProjectIdResponseModel = response.body();
                    List<TaskResponseModel> taskResponseModels =  getTaskByProjectIdResponseModel.getTaskList();
                    List<Task> serverTasks = new ArrayList<>();
                    for (TaskResponseModel taskResponseModel : taskResponseModels) {
                        serverTasks.add(taskResponseModel.toTaskModel());
                    }

                    List<Task> notInLocalTasks = new ArrayList<>(serverTasks);
                    notInLocalTasks.removeAll(localTasks);
                    for (Task task : notInLocalTasks) {
                        taskDao.insert(task);
                    }

                    List<Task> notInServerTasks = new ArrayList<>(localTasks);
                    notInServerTasks.removeAll(serverTasks);
                    for (Task task : notInServerTasks) {
                        taskService.postTask(task.toTaskPostRequestModel(), sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<Response<ResponseBody>>() {
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
            public void onFailure(Call<GetTaskByProjectIdResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
