package com.example.teamtracker.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoogleOAuthLoginResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("status")
    private String status;

    @SerializedName("description")
    private String description;

    public GoogleOAuthLoginResponse(String accessToken, String status, String description) {
        this.accessToken = accessToken;
        this.status = status;
        this.description = description;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
