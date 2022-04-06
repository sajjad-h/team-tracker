package com.example.teamtracker.network.service;

import com.example.teamtracker.network.request.ProjectPostRequestModel;
import com.example.teamtracker.network.response.GetProjectsByOwnerResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProjectService {
    @POST("projects")
    @Headers({"Content-Type: application/json"})
    Call<Response<ResponseBody>> postProject(@Body ProjectPostRequestModel task, @Header("Authorization") String accessToken);

    @GET("projects/owner/{owner}")
    @Headers({"Content-Type: application/json"})
    Call<GetProjectsByOwnerResponseModel> getProjectByOwner(@Path("owner") String owner, @Header("Authorization") String accessToken);
}
