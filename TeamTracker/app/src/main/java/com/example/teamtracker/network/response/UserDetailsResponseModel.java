package com.example.teamtracker.network.response;

import com.google.gson.annotations.SerializedName;

public class UserDetailsResponseModel {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private UserResponseModel user;

    public UserDetailsResponseModel(String status, UserResponseModel user) {
        this.status = status;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserResponseModel getUser() {
        return user;
    }

    public void setUser(UserResponseModel user) {
        this.user = user;
    }
}
