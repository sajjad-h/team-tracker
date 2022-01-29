package com.example.teamtracker.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teamtracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private ImageButton googleLoginButton;
    private CircularProgressButton loginButton;
    private static final String TAG = "login-activity-tag";
    private static final String SERVER_OAUTH_LOGIN_URL = "http://team-tracker.servehttp.com/api/google-oauth-login?idToken=";
    private static final String SERVER_LOGIN_URL = "http://team-tracker.servehttp.com/api/login";
    private ActivityResultLauncher<Intent> loginActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        //To change status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        
        googleLoginButton = (ImageButton) findViewById(R.id.google_login_button);
        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
                    final Context context = getApplicationContext();
                    JSONObject payload = new JSONObject();
                    try {
                        payload.put("email", email);
                        payload.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RequestQueue queue = Volley.newRequestQueue(context);
                                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST,
                                        SERVER_LOGIN_URL, payload,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONObject headersJsonObject = response.getJSONObject("headers");
                                                    String accessToken = headersJsonObject.getString("Authorization");
                                                    Toast.makeText(context, "access_token: " + accessToken, Toast.LENGTH_LONG)
                                                            .show();
                                                    startActivity(new Intent(context, ProtectedActivity.class));
                                                    finish();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, "Wrong email/password!", Toast.LENGTH_LONG).show();
                                                System.out.println(error);
                                            }
                                        }) {

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/json");
                                        params.put("Accept", "application/json");
                                        return params;
                                    }

                                    @Override
                                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                                        JSONArray jsonArray = null;
                                        if (!(response.data == null || response.data.length == 0)) {
                                            try {
                                                String jsonString = new String(response.data,
                                                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                                                jsonArray = new JSONArray(jsonString);
                                            } catch (UnsupportedEncodingException | JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        try {
                                            JSONObject jsonResponse = new JSONObject();
                                            jsonResponse.put("data", jsonArray);
                                            jsonResponse.put("headers", new JSONObject(response.headers));
                                            return Response.success(jsonResponse,
                                                    HttpHeaderParser.parseCacheHeaders(response));
                                        } catch (JSONException je) {
                                            return Response.error(new ParseError(je));
                                        }
                                    }
                                };
                                queue.add(loginRequest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    private void login() {
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
                String idToken = acct.getIdToken();
                sendIdTokenToBackendServer(idToken);
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProtectedActivity.class));
                finish();
            }
        } catch (ApiException e) {
            Log.d(TAG, "loginResult:failed code=" + e.getStatusCode());
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
                            params.put("Content-Type", "application/json");
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

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        finish();
    }
}
