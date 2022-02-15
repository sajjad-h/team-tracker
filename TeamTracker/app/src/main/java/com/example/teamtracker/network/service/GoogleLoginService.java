package com.example.teamtracker.network.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.teamtracker.R;
import com.example.teamtracker.activities.ProtectedActivity;
import com.example.teamtracker.activities.RegisterActivity;
import com.example.teamtracker.network.response.GoogleLoginResponseModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleLoginService {
    private static final String TAG = "GoogleLoginUtil";
    private static GoogleSignInClient instance = null;

    public static GoogleSignInClient getInstance(Context context) {
        if (instance == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.server_client_id))
                    .requestEmail()
                    .build();
            instance = GoogleSignIn.getClient(context, gso);
        }
        return instance;
    }

    public static GoogleLoginResponseModel getGoogleAccountInfo(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();
                String idToken = account.getIdToken();
                return new GoogleLoginResponseModel(personId, personName, personGivenName, personFamilyName, personEmail, personPhoto, idToken);
            }
        } catch (ApiException e) {
            Log.d(TAG, "loginResult:failed code=" + e.getStatusCode());
        }
        return null;
    }
}
