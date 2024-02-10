package com.example.deliveryapp;

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

import org.json.JSONArray;
import org.json.JSONException;

public class managerActivity extends AppCompatActivity {
    private static final String TAG = "HistoreyActivity";
    private TableLayout tableLayout;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        tableLayout = findViewById(R.id.tableLayout2);
        userID = getIntent().getIntExtra("UserID",0);



        fetchOrdersData();
    }
    private void fetchOrdersData(){
        String url = "http://10.0.2.2:5000/orders/all_order_business/"+userID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray foodArray = response.getJSONArray(i);
                                String foodName = foodArray.getString(2);
                                String time = foodArray.getString(5);
                                String cost = foodArray.getString(4);

                                Log.d(TAG, "Food Name: " + foodName);
                                Log.d(TAG, "Time: " + time);
                                Log.d(TAG, "Cost: " + cost);
                                addRowToTable(foodName,time,cost);

                            }
                        } catch (JSONException e) {
                            Log.e("er",e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
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