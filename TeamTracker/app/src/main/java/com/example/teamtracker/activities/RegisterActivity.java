package com.example.teamtracker.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.teamtracker.R;
import com.example.teamtracker.util.SharedRefs;
import com.example.teamtracker.viewmodels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton googleLoginButton;
    private CircularProgressButton registerButton;
    private static final String TAG = "login-activity-tag";
    private ActivityResultLauncher<Intent> loginActivityResultLauncher;
    private SharedRefs sharedRefs;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        sharedRefs = new SharedRefs(getApplicationContext());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loginActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            handleLoginResult(task);
                        }
                    }
                });

        googleLoginButton = (ImageButton) findViewById(R.id.google_login_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithGoogle();
            }
        });


        registerButton = (CircularProgressButton) findViewById(R.id.circularRegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextEmail = findViewById(R.id.editTextEmail);
                String email = editTextEmail.getText().toString();
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();
                EditText editTextName = findViewById(R.id.editTextName);
                String name = editTextName.getText().toString();
                if (email == null || email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                } else if (password == null || password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else if (name == null || name.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Invalid Name", Toast.LENGTH_SHORT).show();
                } else {
                    registerWithForm(name, email, password);
                }
            }
        });
    }

    private void registerWithForm(String name, String username, String password) {
        authViewModel.register(name, username, password).observe(RegisterActivity.this, isRegistrationSuccessful -> {
            if (isRegistrationSuccessful) {
                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Wrong email/password!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent loginIntent = mGoogleSignInClient.getSignInIntent();
        loginActivityResultLauncher.launch(loginIntent);
    }

    private void handleLoginResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                String idToken = acct.getIdToken();
                authViewModel.googleOAuthLogin(idToken).observe(RegisterActivity.this, isLoginSuccessful -> {
                    if (isLoginSuccessful) {
                        Toast.makeText(RegisterActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                        sharedRefs.putString(sharedRefs.USER_NAME, personName);
                        sharedRefs.putString(sharedRefs.USER_EMAIL, personEmail);
                        startActivity(new Intent(this, ProtectedActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Service Error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (ApiException e) {
            Log.d(TAG, "loginResult:failed code=" + e.getStatusCode());
        }
    }

    public void changeStatusBarColor() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}