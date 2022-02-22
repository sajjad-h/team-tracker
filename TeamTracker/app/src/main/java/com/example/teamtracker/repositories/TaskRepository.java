package com.example.teamtracker.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.teamtracker.database.RoomDB;
import com.example.teamtracker.database.dao.TaskDao;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.request.TaskPostRequestModel;
import com.example.teamtracker.network.service.TaskService;
import com.example.teamtracker.util.SharedRefs;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {
    private TaskDao taskDao;
    private TaskService taskService;
    SharedRefs sharedRefs;

    public TaskRepository(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        taskDao = database.taskDao();
        taskService = APIClient.getInstance().create(TaskService.class);
        sharedRefs = new SharedRefs(application);
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
        return taskDao.findTaskByProjectIdLiveData(projectId);
    }

    public void postTask(Task task) {
        taskService.postTask(new TaskPostRequestModel(task.getId().toString(), task.getStartTime(), task.getDuration(), task.getDescription(), task.getTitle(), task.getProjectId()), sharedRefs.getString(SharedRefs.ACCESS_TOKEN, "")).enqueue(new Callback<Response<ResponseBody>>() {
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
