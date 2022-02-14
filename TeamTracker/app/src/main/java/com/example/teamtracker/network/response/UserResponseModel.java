package com.example.teamtracker.network.response;

import com.google.gson.annotations.SerializedName;

public class UserResponseModel {
    @SerializedName(value = "id")
    public int id;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "email")
    private String email;

    public UserResponseModel(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
