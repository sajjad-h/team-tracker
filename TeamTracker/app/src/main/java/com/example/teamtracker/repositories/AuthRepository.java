package com.example.teamtracker.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.request.LoginRequestModel;
import com.example.teamtracker.network.service.AuthService;
import com.example.teamtracker.network.response.GoogleOAuthLoginResponse;
import com.example.teamtracker.util.SharedRefs;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;

public class AuthRepository {
    private AuthService authService;
    Observable<Boolean> isLoginSuccessful;
    SharedRefs sharedRefs;
    private Object Response;

    public AuthRepository(Context context) {
        authService = APIClient.getInstance().create(AuthService.class);
        sharedRefs = new SharedRefs(context);
    }

    public LiveData<Boolean> login(String username, String password) {
        MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
        authService.login(new LoginRequestModel(username, password)).enqueue(new Callback<retrofit2.Response<Void>>() {
            @Override
            public void onResponse(Call<retrofit2.Response<Void>> call, retrofit2.Response<retrofit2.Response<Void>> response) {
                String accessToken = response.headers().get(SharedRefs.AUTHORIZATION);
                sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                isLoginSuccessful.setValue(true);
            }

            @Override
            public void onFailure(Call<retrofit2.Response<Void>> call, Throwable t) {
                t.printStackTrace();
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }

    public LiveData<Boolean> googleOAuthLogin(String googleIdToken) {
        MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
        authService.googleOAuthLogin(googleIdToken).enqueue(new Callback<GoogleOAuthLoginResponse>() {
            @Override
            public void onResponse(Call<GoogleOAuthLoginResponse> call, retrofit2.Response<GoogleOAuthLoginResponse> response) {
                GoogleOAuthLoginResponse googleOAuthLoginResponse = response.body();
                String status = googleOAuthLoginResponse.getStatus();
                if (status.equals("OK")) {
                    String accessToken = googleOAuthLoginResponse.getAccessToken();
                    sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                    isLoginSuccessful.setValue(true);
                } else {
                    isLoginSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<GoogleOAuthLoginResponse> call, Throwable t) {
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }
}
