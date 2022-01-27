package com.example.teamtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main-activity-tag";
    private static final String SERVER_OAUTH_LOGIN_URL = "http://team-tracker.servehttp.com/api/google-oauth-login?idToken=";
    private GoogleSignInClient mGoogleSignInClient = null;
    private final Integer RC_SIGN_IN = 100;
    private SignInButton signInButton;
    private Button signOutButton;
    private TextView helloTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = findViewById(R.id.sign_out_button);
        helloTextView = (TextView) findViewById(R.id.helloText);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (account == null) {
            makeSignedOutButtonState();
        } else {
            makeSignedInButtonState();
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void makeSignedInButtonState() {
        signInButton.setVisibility(View.GONE);
        signOutButton.setVisibility(View.VISIBLE);
    }

    private void makeSignedOutButtonState() {
        signInButton.setVisibility(View.VISIBLE);
        signOutButton.setVisibility(View.GONE);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        handleSignOutResult();
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                String idToken = acct.getIdToken();
                System.out.println(idToken);
                sendIdTokenToBackendServer(idToken);
                Uri personPhoto = acct.getPhotoUrl();
                helloTextView.setText("Hello " + personName);
                makeSignedInButtonState();
                Toast.makeText(this, "Successfully Signed In!", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            Log.d(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void sendIdTokenToBackendServer(final String idToken) {
        final Context context = getApplicationContext();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            SERVER_OAUTH_LOGIN_URL + idToken,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String status = jsonObject.getString("status");
                                        if (status.equals("OK")) {
                                            String accessToken = jsonObject.getString("access_token");
                                            Toast.makeText(context, "access_token: " + accessToken, Toast.LENGTH_LONG)
                                                    .show();
                                        } else {
                                            Toast.makeText(context, "status: " + status, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error);
                                }
                            }) {
                        /**
                         * Work only on POST method
                         *
                         * @Override
                         *           protected Map<String,String> getParams(){
                         *           Map<String,String> params = new HashMap<String, String>();
                         *           params.put("idToken", idToken);
                         *           return params;
                         *           }
                         */

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }
                    };
                    queue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void handleSignOutResult() {
        makeSignedOutButtonState();
        Toast.makeText(this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
        helloTextView.setText("Hello World!");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
