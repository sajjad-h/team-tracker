package com.example.teamtracker.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.teamtracker.R;
import com.example.teamtracker.network.response.GoogleLoginResponseModel;
import com.example.teamtracker.network.service.GoogleLoginService;
import com.example.teamtracker.viewmodels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ImageButton googleLoginButton;
    private CircularProgressButton loginButton;
    private ActivityResultLauncher<Intent> loginActivityResultLauncher;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loginActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            GoogleLoginResponseModel googleLoginResponseModel = GoogleLoginService.getGoogleAccountInfo(task);
                            authViewModel.googleOAuthLogin(googleLoginResponseModel).observe(LoginActivity.this, isLoginSuccessful -> {
                                if (isLoginSuccessful) {
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(LoginActivity.this, ProtectedActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Service Error!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

        //To change status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        googleLoginButton = (ImageButton) findViewById(R.id.google_login_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });

        loginButton = (CircularProgressButton) findViewById(R.id.circularLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                String email = editTextEmail.getText().toString();
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();
                if (email == null || email.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                } else if (password == null || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else {
                    loginWithForm(email, password);
                }
            }
        });
    }

    private void loginWithForm(String username, String password) {
        authViewModel.login(username, password).observe(LoginActivity.this, isLoginSuccessful -> {
            if (isLoginSuccessful) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ProtectedActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Wrong email/password!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginWithGoogle() {
        GoogleSignInClient mGoogleSignInClient = GoogleLoginService.getInstance(this);
        Intent loginIntent = mGoogleSignInClient.getSignInIntent();
        loginActivityResultLauncher.launch(loginIntent);
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        finish();
    }
}
