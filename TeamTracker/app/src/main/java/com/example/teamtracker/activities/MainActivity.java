package com.example.teamtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.teamtracker.R;
import com.example.teamtracker.util.AuthUtil;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AuthUtil.isLogin(this)) {
            startActivity(new Intent(this, ProtectedActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
