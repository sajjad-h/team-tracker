package com.example.teamtracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.teamtracker.fragments.DemoPagerFragment;
import com.example.teamtracker.R;
import com.example.teamtracker.fragments.DemoFragment;
import com.example.teamtracker.fragments.HomeFragment;

import com.example.teamtracker.util.SharedRefs;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class ProtectedActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    DrawerLayout drawer;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation_view;
    FragmentManager fragmentManager;
    SharedRefs sharedRefs;
    TextView userNameTextView;
    TextView userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);

        sharedRefs = new SharedRefs(getApplicationContext());
        setSupportActionBar(findViewById(R.id.toolbar));

        navigation_view = findViewById(R.id.navigation_view);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });

        userNameTextView = navigation_view.getHeaderView(0).findViewById(R.id.user_name);
        userEmailTextView = navigation_view.getHeaderView(0).findViewById(R.id.user_email);

        userNameTextView.setText(sharedRefs.getString(sharedRefs.USER_NAME, "user name not found"));
        userEmailTextView.setText(sharedRefs.getString(sharedRefs.USER_EMAIL, "user email not found"));

        drawer = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new HomeFragment(getApplicationContext());
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_demo_pager: // It is a demo pager (it is here just for learning purpose)
                fragment = new DemoPagerFragment();
                break;
            case R.id.nav_demo:
                fragment = DemoFragment.newInstance("Demo Fragment");
                break;
            case R.id.nav_logout:
                logout();
                return;
            default:
                fragment = new HomeFragment(getApplicationContext());
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sharedRefs.remove(sharedRefs.ACCESS_TOKEN);
        sharedRefs.remove(sharedRefs.USER_NAME);
        sharedRefs.remove(sharedRefs.USER_EMAIL);
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