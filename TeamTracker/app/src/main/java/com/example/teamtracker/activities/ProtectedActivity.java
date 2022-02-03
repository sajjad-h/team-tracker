package com.example.teamtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamtracker.R;
import com.example.teamtracker.util.AdapterForHomeRecyclerView;
import com.example.teamtracker.util.AuthUtil;
import com.example.teamtracker.util.ModelClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProtectedActivity extends AppCompatActivity {

    private Button logoutButton;
    private GoogleSignInClient mGoogleSignInClient;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> projectList;
    AdapterForHomeRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        initData();
        initRecyclerView();

        logoutButton = (Button) findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.projectsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AdapterForHomeRecyclerView(projectList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        projectList = new ArrayList<>();

        projectList.add(new ModelClass("University Management System"));
        projectList.add(new ModelClass("Book Sharing Platform UI Design"));
        projectList.add(new ModelClass("College Portal Development"));
        projectList.add(new ModelClass("Library Management System UI Design"));
        projectList.add(new ModelClass("Demo Facebook Clone App Project"));


    }

    private void logout() {
        AuthUtil.removeAccessToken(getApplicationContext());
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        handleLogoutResult();
                    }
                });
    }

    private void handleLogoutResult() {
        Toast.makeText(this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}