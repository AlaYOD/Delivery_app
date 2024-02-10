package com.example.deliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AddToCart extends AppCompatActivity {

    private TextView txtFoodName;
    private ImageView imgFood;
    private Button add, remove;
    private ImageView back;

    private TextView txtFoodDesc;
    private TextView txtFoodPrice;
    private TextView quantity;
    private Button btnOrder;
    private SharedPreferences prefs;

    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private Food currFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcart);

        setupViews();
        setupPrefs();

        String str = prefs.getString("Food", "");
        currFood = gson.fromJson(str, Food.class);

        txtFoodDesc.setText("Time Estimated: " + currFood.getDesc());
        txtFoodName.setText(currFood.getName());
        txtFoodPrice.setText("Price Per one: " + currFood.getPrice());

        String imageName = currFood.getImg();
        int resourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        if (resourceId != 0) {
            imgFood.setImageResource(resourceId);
        } else {
            imgFood.setImageResource(R.drawable.baseline_settings_24);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                currentQuantity++;
                quantity.setText(String.valueOf(currentQuantity));
                remove.setEnabled(true);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantity.setText(String.valueOf(currentQuantity));
                }
                if (currentQuantity == 1) {
                    remove.setEnabled(false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBackToFoods();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
                navigateBackToFoods();
            }
        });
    }

    private void setupPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }

    private void setupViews() {
        txtFoodName = findViewById(R.id.foodName);
        txtFoodDesc = findViewById(R.id.txtFoodDesc);
        imgFood = findViewById(R.id.imgFood);
        btnOrder = findViewById(R.id.btnAddToCart);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        add = findViewById(R.id.addBtn);
        remove = findViewById(R.id.removeBtn);
        quantity = findViewById(R.id.numberSelected);
        back = findViewById(R.id.back);
    }

    private void addToCart() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.15:5000/add-cart/"
                + 1 + "/" + currFood.getFoodId() + "/" + Integer.parseInt(quantity.getText().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json response", response.toString());
                        // Handle response if needed
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error occurred", "Error occurred while adding item to cart: " + error.getMessage());
                Toast.makeText(AddToCart.this, "Failed to add item to cart. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void navigateBackToFoods() {
        Intent intent = new Intent(AddToCart.this, Foods.class);
        startActivity(intent);
        finish();
    }
}
