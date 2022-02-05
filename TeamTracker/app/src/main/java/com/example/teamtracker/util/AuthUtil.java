package com.example.teamtracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthUtil {
    private Context context;
    private SharedRefs sharedRefs;

    public AuthUtil(Context context, SharedRefs sharedRefs) {
        this.context = context;
        this.sharedRefs = sharedRefs;
    }

    public boolean isLogin() {
        return sharedRefs.has(sharedRefs.ACCESS_TOKEN);
    }
}
