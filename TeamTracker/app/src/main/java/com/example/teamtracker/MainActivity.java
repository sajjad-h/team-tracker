package com.example.teamtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String HELLO_API_CALL_URL = "http://agdum-bagdum.000webhostapp.com/hello.php";
    private TextView helloTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloTextView = findViewById(R.id.helloText);
        button = findViewById(R.id.refreshButton);
        helloAPICall();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloAPICall();
            }
        });
    }

    void helloAPICall() {
        final Context context = getApplicationContext();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, HELLO_API_CALL_URL,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String helloText = jsonObject.getString("helloText");
                                        Toast.makeText(context, "helloText: " + helloText, Toast.LENGTH_SHORT).show();
                                        helloTextView.setText(helloText);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error);
                        }
                    }
                    );
                    queue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
