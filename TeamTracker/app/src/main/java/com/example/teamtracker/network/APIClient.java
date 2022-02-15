package com.example.teamtracker.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String BASE_URL = "http://team-tracker.servehttp.com/api/";
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthorizationInterceptor())
                    //.readTimeout(45,TimeUnit.SECONDS)
                    //.writeTimeout(45,TimeUnit.SECONDS)
                    .build();
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}

class AuthorizationInterceptor implements Interceptor {
    String accessToken = "";

    public AuthorizationInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header("No-Authentication") == null) {
            if (!accessToken.isEmpty()) {
                request = request.newBuilder()
                        .addHeader("Authorization", accessToken)
                        .build();
            }
        }
        return chain.proceed(request);
    }
}