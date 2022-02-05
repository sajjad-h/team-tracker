package com.example.teamtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.teamtracker.R;
import com.example.teamtracker.util.AuthUtil;
import com.example.teamtracker.util.SharedRefs;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private AuthUtil authUtil;
    private SharedRefs sharedRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        sharedRefs = new SharedRefs(getApplicationContext());
        authUtil = new AuthUtil(getApplicationContext(), sharedRefs);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (authUtil.isLogin()) {
                    intent = new Intent(getApplicationContext(), ProtectedActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1000);
    }
}