package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.deliveryapp.models.Food;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Foods extends AppCompatActivity implements RecyclerViewInterface {

    private RecyclerView recFoods;
    private TextView txtNoFoods;
    private TextView txtFoodDesc;
    private Items currItem;
    private ArrayList<Food> foods = new ArrayList<Food>();

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        setupSharedPrefs();
        String str = prefs.getString("ITEM", "");
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

        Food food1=new Food("Classic Burger","big joucy teasty burger", R.drawable.burger,13.9,"Burger");
        Food food2=new Food("Super Burger","big joucy teasty burger2", R.drawable.maramia,18.9,"Burger");
        Food food3=new Food("super double Burger","big joucy teasty burger3", R.drawable.vanila,20,"Burger");
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);

        if (foods.size() == 0) {
            txtNoFoods.setVisibility(View.VISIBLE);
        } else {

            Food_Adapter Fadapter = new Food_Adapter(this, foods, this);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

            recFoods.setLayoutManager(layoutManager);
            recFoods.setAdapter(Fadapter);
        }
    }


    private void setupViews() {
        recFoods = findViewById(R.id.recFoods);
        txtNoFoods = findViewById(R.id.txtNoFood);
        txtFoodDesc = findViewById(R.id.txtItemDesc);

    }

    @Override
    public void onItemClick(int pos) {
        Food currFood = foods.get(pos);

        String foodString = gson.toJson(currFood);

        editor.putString("FOOD", foodString);
        editor.commit();

        //Intent intent = new Intent(Foods.this, FoodInfo.class);
        //   startActivity(intent);

        // Toast.makeText(Rooms.this, currRoom.getName(), Toast.LENGTH_SHORT).show();


    }
}
