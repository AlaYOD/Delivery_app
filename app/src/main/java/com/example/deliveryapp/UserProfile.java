package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {
    TextView name_text_view;
    TextView email_text_view;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String EMAIL = "EMAIL";
    private AppCompatButton button;
    private  AppCompatButton history_btn;
    private int userID;

    AppCompatButton business_manager_btn;
    AppCompatButton walent_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name_text_view = findViewById(R.id.NameTextView);
        email_text_view = findViewById(R.id.EmailTextView);
        business_manager_btn = findViewById(R.id.businessManagerBtn);
        walent_btn = findViewById(R.id.walentbtn);

        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        String email = prefs.getString(EMAIL,"");
        Toast.makeText(this,"Email: "+email,Toast.LENGTH_LONG).show();
        if(!email.isEmpty()){
            Log.e("checkUser",email.toString());
            try {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://10.0.2.2:5000/user/" + email;  // Ensure 'email' is properly defined and encoded

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Log.e("hi","0");
                                    // Extract data from the response
                                     userID = response.getInt(0); // UserID
                                    String userName = response.getString(1); // UserName
                                    String userMobile = response.getString(2); // UserMobile
                                    String role = response.getString(3); // Role

                                    if(role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("admin")){
                                        business_manager_btn.setVisibility(View.VISIBLE);
                                        walent_btn.setVisibility(View.VISIBLE);
                                    }


                                    String userEmail = response.getString(4); // Email
                                    String userPass = response.getString(5);
                                    String str = "userID: " + userID + " UserName: " + userName + " Mobile: " + userMobile + " Role: " + role;
                                    Log.e("hi","1");
                                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                                    Log.e("hi","2");
                                    // Save the data in SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("UserID", userID);
                                    editor.putString("UserName", userName);
                                    editor.putString("UserMobile", userMobile);
                                    editor.putString("Role", role);
                                    editor.putString("Email", userEmail);  // Use userEmail
                                    editor.putString("UserPass",userPass);
                                    editor.apply();
                                    Log.e("hi","3");

                                    name_text_view.setText(userName);
                                    email_text_view.setText(userEmail);

                                } catch (JSONException e) {
                                    Log.e("JSON Error", "Error parsing JSON: " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = "Error: ";
                        Log.e("hi","4");
                        if (error.networkResponse != null) {
                            errorMsg += "Response Code: " + error.networkResponse.statusCode;
                            // Attempt to parse server response if available
                            if (error.networkResponse.data != null) {
                                try {
                                    String responseBody = new String(error.networkResponse.data, "utf-8");
                                    JSONObject data = new JSONObject(responseBody);
                                    String serverMessage = data.optString("message", "Error response from server");
                                    errorMsg += ", Message: " + serverMessage;
                                } catch (Exception e) {
                                    errorMsg += ", Error parsing error response";
                                }
                            }
                        } else {
                            errorMsg += error.toString();
                        }
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(jsonArrayRequest);
            } catch (Exception e) {
                Log.e("Request Error", "Error creating request: " + e.getMessage());
            }

        }

        button = findViewById(R.id.updateUserInfoData);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,personalInformation.class);
                startActivity(intent);
            }
        });

        history_btn = findViewById(R.id.historybtn);
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, historyTableView.class);
                intent.putExtra("UserID",userID);
                startActivity(intent);
            }
        });

        business_manager_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,managerActivity.class);
                intent.putExtra("UserID",userID);
                startActivity(intent);
            }
        });

        walent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,walentView.class);
                intent.putExtra("UserID",userID);
                startActivity(intent);
            }
        });

    }
}