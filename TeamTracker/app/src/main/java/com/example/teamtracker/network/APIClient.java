package com.example.teamtracker.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String BASE_URL = "http://team-tracker.servehttp.com/api/";
    public static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
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