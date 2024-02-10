package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class personalInformation extends AppCompatActivity {

    EditText update_user_name;
    EditText update_user_email;
    EditText update_user_mobile;
    EditText update_user_pass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String PASS = "PASS";
    public static final String EMAIL = "EMAIL";

    AppCompatButton updata_user_data_btn;
    SharedPreferences prefs;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        update_user_name= findViewById(R.id.updateUserName);
        update_user_email= findViewById(R.id.updateUserEmail);
        update_user_mobile=findViewById(R.id.updateUserMobile);
        update_user_pass=findViewById(R.id.updateUserPass);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        update_user_name.setText(sharedPreferences.getString("UserName",""));
        update_user_email.setText(sharedPreferences.getString("Email",""));
        update_user_mobile.setText(sharedPreferences.getString("UserMobile",""));
        update_user_pass.setText(sharedPreferences.getString("UserPass",""));

        updata_user_data_btn = findViewById(R.id.updateUserInfo_btn);

        updata_user_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = update_user_name.getText().toString();
                String userEmail = update_user_email.getText().toString();
                String userMobile = update_user_mobile.getText().toString();
                String userPass = update_user_pass.getText().toString();

               updateUserInfo(userName,userEmail,userMobile,userPass);
            }
        });


    }

    private void updateUserInfo(String userName, String userEmail, String userMobile, String userPass) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:5000/update-user/" + userEmail;

        JSONObject jsonBody = new JSONObject();
        try {
            Log.i("user",userName);
            jsonBody.put("UserName", userName);
            Log.i("seters",userName);
            jsonBody.put("UserMobile", userMobile);
            jsonBody.put("UserPass", userPass);
            jsonBody.put("Email",userEmail);
            Log.i("seters",userEmail);

            // Create a JSONArray and add the JSONObject
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonBody);

            // Use JsonArrayRequest
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.PUT, url, jsonArray,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Handle success
                            Toast.makeText(getApplicationContext(), "User updated Info successfully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            queue.add(jsonArrayRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}