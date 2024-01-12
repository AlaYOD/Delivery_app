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
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                Log.d("MyTag", "This is a debug message ooo");

                if(TextUtils.isEmpty(email)){
                    Log.d("MyTag", "This is a debug message 3");

                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Log.d("MyTag", "This is a debug message 4");

                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("MyTag", "This is a debug message 5");

                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                                    String name = editTextEmail.getText().toString().trim();
                                    String password = editTextPassword.getText().toString().trim();

                                    if(checkbox.isChecked()){
                                        if(!flag) {
                                            editor.putString(NAME, name);
                                            editor.putString(PASS, password);
                                            editor.putBoolean(FLAG, true);
                                            editor.commit();
                                        }

                                    }
                                    Log.d("MyTag", "This is a debug message 6");

                                    Intent intent = new Intent(MainActivity.this, home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
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