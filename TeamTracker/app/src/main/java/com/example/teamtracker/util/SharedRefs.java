package com.example.teamtracker.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedRefs {
    private final String PREFS_FILENAME = "team_tracker_prefs";
    private SharedPreferences preferences;
    private Context context;

    public final String ACCESS_TOKEN = "access_token";
    public final String USER_NAME = "user_name";
    public final String USER_EMAIL = "user_email";

    public SharedRefs(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public boolean has(String key) {
        return preferences.contains(key);
    }

    public void remove(String key) {
        preferences.edit().remove(key).commit();
    }
}
