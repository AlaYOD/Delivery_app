package com.example.deliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    CheckBox checkbox;
    Button buttonLogin;
    FirebaseAuth mAuth;

    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    ProgressBar progressBar;


    EditText editTextEmail, editTextPassword;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        buttonLogin = findViewById(R.id.btn_login);

        checkbox = findViewById(R.id.checkBox);
        mAuth = FirebaseAuth.getInstance();


        setupSharedPrefs();
        checkPrefs();

        textView = findViewById(R.id.sigb_up);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sign_in.class);
                startActivity(intent);
            }
        });

        Log.e("MyTag", "This is a debug message 1");

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MyTag", "This is a debug message 2");

                progressBar.setVisibility(View.VISIBLE);
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);

                if (TextUtils.isEmpty(email)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                String url = "http://10.0.2.2:5000/login";

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("email", email);
                    json.put("password", password);
                } catch (JSONException e) {
                    Log.e("JSON Error", "Error creating JSON: ", e);
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                RequestBody body = RequestBody.create(JSON, json.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(() -> {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(() -> progressBar.setVisibility(View.GONE));
                        final String myResponse;
                        if (response.body() != null) {
                            myResponse = response.body().string(); // Read the response
                        } else {
                            myResponse = "No response body";
                        }
                        runOnUiThread(() -> {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Success: " + myResponse, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle unsuccessful response, e.g., server returned error code
                                Toast.makeText(MainActivity.this, "Error: " + myResponse, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }






        });

    }
    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            editTextEmail.setText(name);
            editTextPassword.setText(password);
            checkbox.setChecked(true);
        }
    }

}