package com.example.teamtracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthUtil {
    public static boolean isLogin(Context context) {
        String accessToken = getAccessToken(context);
        if (accessToken.isEmpty()) {
            return false;
        }
        return true;
    }

    public static void storeAccessToken(Context context, String accessToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("access_token", accessToken);
        editor.apply();
    }

    public static String getAccessToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = preferences.getString("access_token", "");
        return accessToken;
    }

    public static void removeAccessToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("access_token", "");
        editor.apply();
    }
}
