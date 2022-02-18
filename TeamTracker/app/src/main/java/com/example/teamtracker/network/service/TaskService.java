package com.example.teamtracker.network.service;

import com.example.teamtracker.network.request.TaskPostRequestModel;
import com.example.teamtracker.network.response.GetTaskByProjectIdResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaskService {
    @POST("tasks")
    @Headers({"Content-Type: application/json"})
    Call<Response<ResponseBody>> postTask(@Body TaskPostRequestModel task, @Header("Authorization") String accessToken);

    @GET("tasks/projects/{projectId}")
    @Headers({"Content-Type: application/json"})
    Call<GetTaskByProjectIdResponseModel> getTaskByProjectId(@Path("projectId") int projectId, @Header("Authorization") String accessToken);
}
