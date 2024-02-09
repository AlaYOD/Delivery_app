package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Food;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Foods extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recFoods;
    private TextView txtNoFoods;
    private TextView txtFoodDesc;
    private Items currItem;
    private Food_Adapter Fadapter;
    private ArrayList<Food> foods = new ArrayList<>();

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        setupSharedPrefs();
         str = prefs.getString("ITEM", "");
        currItem = gson.fromJson(str, Items.class);

        setupViews();

        txtFoodDesc.setText(currItem.getTitle());
        addFoods();
    }

    private void setupSharedPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }

    private void addFoods() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15:5000/get-food/"+currItem.getTitle()+"/"+currItem.getId();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json response", response.toString());

                        try {
                            JSONArray foodArray = response.getJSONArray("response");
                            for (int i = 0; i < foodArray.length(); i++) {
                                JSONObject foodJson = foodArray.getJSONObject(i);
                                String foodName = foodJson.getString("FoodName");
                                String imageString = foodJson.getString("Image");
                                double foodPrice = foodJson.getDouble("Cost");
                                int foodID=foodJson.getInt("FoodId");
                                String foodTimeStr = foodJson.optString("Time", "");
                                Food food1 = new Food();
                                food1.setImg(imageString);
                                food1.setPrice(foodPrice);
                                food1.setName(foodName);
                                food1.setTime(foodTimeStr);
                                food1.setFoodId(foodID);
                                foods.add(food1);
                            }

                            if (foods.size() == 0) {
                                txtNoFoods.setVisibility(View.VISIBLE);
                            } else {
                                Fadapter = new Food_Adapter(Foods.this, foods, Foods.this);
                                Toast.makeText(Foods.this, foods.get(0).getImg()+"", Toast.LENGTH_SHORT).show();
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Foods.this, 2);
                                recFoods.setLayoutManager(layoutManager);
                                recFoods.setAdapter(Fadapter);
                                Fadapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Foods.this, "Error occurred while parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error occurred", "Error occurred while fetching foods: " + error.getMessage());
                Toast.makeText(Foods.this, "Error occurred while fetching foods", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }


    private void setupViews() {
        recFoods = findViewById(R.id.recFoods);
        txtNoFoods = findViewById(R.id.txtNoFood);
        txtFoodDesc = findViewById(R.id.txtItemDesc);
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos < foods.size()) {
            Food currFood = foods.get(pos);
            String foodString = gson.toJson(currFood);
            editor.putString("Food", foodString);
            editor.apply();
            Intent intent = new Intent(Foods.this, AddToCart.class);
            startActivity(intent);
        } else {
            Log.e("onItemClick", "Invalid position: " + pos);
        }
    }
}
