package com.example.teamtracker.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.teamtracker.network.response.GoogleLoginResponseModel;
import com.example.teamtracker.repositories.AuthRepository;

import java.io.IOException;

import io.reactivex.Observable;


public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    Observable<Boolean> isLoginSuccessful;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<Boolean> login(String username, String password) {
        return authRepository.login(username, password);
    }

    public LiveData<Boolean> googleOAuthLogin(GoogleLoginResponseModel googleLoginResponseModel) {
        return authRepository.googleOAuthLogin(googleLoginResponseModel);
    }

    public LiveData<Boolean> register(String name, String username, String password) {
        return authRepository.register(name, username, password);
    }
}
