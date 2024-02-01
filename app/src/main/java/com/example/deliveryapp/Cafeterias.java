package com.example.deliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Business;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cafeterias extends AppCompatActivity implements RecyclerViewInterface {

    private DrawerLayout drawerLayout;
    private RecyclerView recCafeteria;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private ArrayList<Business> businesses = new ArrayList<>();
    private Cafeteria_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);

        setupPrefs();
        setupViews();
        getBusinessData();
    }

    private void setupPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }

    private void getBusinessData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:5000/businesses"; // Replace with your server URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject business = response.getJSONObject(i);
                                Business businessObj = new Business(
                                        business.getInt("BusinessID"),
                                        business.getString("BusinessName"),
                                        business.getString("Manager"),
                                        business.getString("Mobile"),
                                        business.getInt("UserID"));
                                businesses.add(businessObj);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.d("Cafeteria", "Error Response code: " + error.networkResponse.statusCode);
                        }
                        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(jsonArrayRequest);
    }

    private void setupViews() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        recCafeteria = findViewById(R.id.recCafeteria);

        recCafeteria.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Cafeteria_Adapter(this, businesses, this);
        recCafeteria.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int pos) {
        // Assuming you want to pass the clicked business details to another activity
        Business clickedBusiness = businesses.get(pos);

        // Converting the clicked business object to a JSON string
        String businessJson = gson.toJson(clickedBusiness);

        // Storing the JSON string in SharedPreferences
        editor.putString("selectedBusiness", businessJson);
        editor.commit();

        // Navigating to another activity (e.g., ViewBusinessDetailsActivity)
        Intent intent = new Intent(Cafeterias.this, ViewCafeteriaFood.class);
        startActivity(intent);
    }
}
