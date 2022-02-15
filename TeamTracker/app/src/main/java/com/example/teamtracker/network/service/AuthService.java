package com.example.teamtracker.network.service;

import com.example.teamtracker.network.request.LoginRequestModel;
import com.example.teamtracker.network.request.RegisterRequestModel;
import com.example.teamtracker.network.response.GoogleOAuthLoginResponseModel;
import com.example.teamtracker.network.response.RegisterResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {
    @POST("login")
    @Headers({"Content-Type: application/json"})
    Call<Response<Void>> login(@Body LoginRequestModel loginRequestModel);

    @GET("google-oauth-login")
    @Headers({"Content-Type: application/json"})
    Call<GoogleOAuthLoginResponseModel> googleOAuthLogin(@Query("idToken") String googleIdToken);

    @POST("register")
    @Headers({"Content-Type: application/json"})
    Call<RegisterResponseModel> register(@Body RegisterRequestModel registerRequestModel);
}
