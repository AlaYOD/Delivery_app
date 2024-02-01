package com.example.deliveryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.deliveryapp.databinding.ActivitySignInBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Sign_in extends AppCompatActivity {


    CheckBox checkbox;
    Button buttonLogin;
    FirebaseAuth mAuth;
    Button buttonReg;
    TextView sign_up;

    ProgressBar progressBar;


    EditText editTextEmail, editTextPassword,editTextPassConf,editTextUserName,editUserMobile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        buttonReg = findViewById(R.id.btn_Register);
        progressBar = findViewById(R.id.progressBar2);

        editTextEmail = findViewById(R.id.email);
        editTextUserName = findViewById(R.id.userName);
        editTextPassword = findViewById(R.id.password);
        sign_up = findViewById(R.id.sigb_up);
        editUserMobile = findViewById(R.id.userMobile);
        editTextPassConf = findViewById(R.id.editConfermPassword);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email,mobileNum, password,passConf,userName;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                userName = editTextUserName.getText().toString();
                passConf = editTextPassConf.getText().toString();
                mobileNum = editUserMobile.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Sign_in.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(Sign_in.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mobileNum)){
                    Toast.makeText(Sign_in.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(Sign_in.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Sign_in.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passConf)){
                    Toast.makeText(Sign_in.this, "Enter passwordConfermation", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                OkHttpClient client = new OkHttpClient();
                String url = "http://10.0.2.2:5000/register";
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("username",userName);
                    json.put("email", email);
                    json.put("usermobile",mobileNum);
                    json.put("password", password);
                    json.put("role","agent");
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
                                Toast.makeText(Sign_in.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Sign_in.this, "Success: " + myResponse, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Sign_in.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Handle unsuccessful response, e.g., server returned error code
                                }
                            });
                        }
                    });

            }
        });
    }


}