package com.example.teamtracker.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamtracker.network.APIClient;
import com.example.teamtracker.network.request.LoginRequestModel;
import com.example.teamtracker.network.request.RegisterRequestModel;
import com.example.teamtracker.network.response.GoogleLoginResponseModel;
import com.example.teamtracker.network.response.RegisterResponseModel;
import com.example.teamtracker.network.service.AuthService;
import com.example.teamtracker.network.response.GoogleOAuthLoginResponseModel;
import com.example.teamtracker.util.SharedRefs;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                if (response.code() == 200) {
                    String accessToken = response.headers().get(SharedRefs.AUTHORIZATION);
                    sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                    isLoginSuccessful.setValue(true);
                }
                else {
                    isLoginSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<retrofit2.Response<Void>> call, Throwable t) {
                t.printStackTrace();
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }

    public LiveData<Boolean> googleOAuthLogin(GoogleLoginResponseModel googleLoginResponseModel) {
        MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
        authService.googleOAuthLogin(googleLoginResponseModel.getIdToken()).enqueue(new Callback<GoogleOAuthLoginResponseModel>() {
            @Override
            public void onResponse(Call<GoogleOAuthLoginResponseModel> call, retrofit2.Response<GoogleOAuthLoginResponseModel> response) {
                GoogleOAuthLoginResponseModel googleOAuthLoginResponseModel = response.body();
                String status = googleOAuthLoginResponseModel.getStatus();
                if (status.equals("OK")) {
                    String accessToken = googleOAuthLoginResponseModel.getAccessToken();
                    sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                    sharedRefs.putString(SharedRefs.USER_NAME, googleLoginResponseModel.getDisplayName());
                    sharedRefs.putString(SharedRefs.USER_EMAIL, googleLoginResponseModel.getEmail());
                    isLoginSuccessful.setValue(true);
                } else {
                    isLoginSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<GoogleOAuthLoginResponseModel> call, Throwable t) {
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }

    public LiveData<Boolean> register(String name, String email, String password) {
        MutableLiveData<Boolean> isRegistrationSuccessful = new MutableLiveData<>();
        authService.register(new RegisterRequestModel(name, email, password)).enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, retrofit2.Response<RegisterResponseModel> response) {
                RegisterResponseModel registerResponseModel = response.body();
                String status = registerResponseModel.getStatus();
                if (status.equals("OK")) {
                    isRegistrationSuccessful.setValue(true);
                } else {
                    isRegistrationSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                isRegistrationSuccessful.setValue(false);
            }
        });
        return isRegistrationSuccessful;
    }
}
