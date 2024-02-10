package com.example.deliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Driver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class walentView extends AppCompatActivity {

    private static final String TAG = "HistoreyActivity";
    private TableLayout tableLayout;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walent_view);
        userID = getIntent().getIntExtra("UserID",0);

        tableLayout = findViewById(R.id.tableLayout3);
        Log.d("ard","-2");

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("Drivers");
        Log.d("ard","-1");

        Driver d= new Driver("hasan","059750443","32423532532");
        ref.push().setValue(d);
        Driver d1= new Driver("mohammad","059750111","32423532532");
        ref.push().setValue(d1);
        Driver d2= new Driver("manar","059722443","87923533532");
        ref.push().setValue(d2);
        Log.d("ard","0");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ard","1");
                if (snapshot.exists()) {
                    List<String> drivers = new ArrayList<>();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Driver driver = childSnapshot.getValue(Driver.class);
                        if (driver != null && !drivers.contains(driver.getName().toLowerCase())) {
                            // Add the driver's name in lowercase to avoid case-sensitive duplicates
                            drivers.add(driver.getName().toLowerCase());

                            // Since this driver is unique, add their info to the table
                            try {
                                addRowToTable(driver.getName(), driver.getMobile(), driver.getSn());
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error",error.toString());
            }
        });



    }


    private void addRowToTable(String foodName, String foodTime, String foodCost) throws JSONException{
        TableRow tableRow = new TableRow(this);

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        tableRow.setLayoutParams(layoutParams);

        TextView textView1 = new TextView(this);
        textView1.setText(foodName);
        textView1.setPadding(10, 10, 10, 10);
        textView1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
        Log.d(TAG, "FoodName: " + foodName);



        TextView textView2 = new TextView(this);
        textView2.setText(foodTime);
        textView2.setPadding(10, 10, 10, 10);
        textView2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        Log.d(TAG, "Time: " + foodTime);



        TextView textView3 = new TextView(this);
        textView3.setText(foodCost);
        textView3.setPadding(10, 10, 10, 10);
        textView3.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
        Log.d(TAG, "Cost: " + foodCost);



        tableRow.addView(textView1);
        tableRow.addView(textView2);
        tableRow.addView(textView3);

        tableLayout.addView(tableRow);
    }
}