package com.example.deliveryapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp.models.Cafeteria;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.deliveryapp.models.Business;
public class ViewCafeteriaFood extends AppCompatActivity implements RecyclerViewInterface {

    private DrawerLayout drawerLayout;
    private ImageView image_chosen;
    private TextView txtChosen;
    private RecyclerView recFood;
    Business  currCafeteria;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private boolean flag = false;
    public static final String FLAG = "FLAG";

    private ArrayList<Items> types = new ArrayList<Items>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.foods_layout);
        setupPrefs();
        // Toast.makeText(this, prefs.getString("Cafeteria1",""), Toast.LENGTH_SHORT).show();
        String str = prefs.getString("selectedBusiness","");
        Log.e("choser","000");

        if(str!=null && !str.isEmpty()) {

            currCafeteria = gson.fromJson(str, Business.class);
            Log.e("choser","001");
             Toast.makeText(this, currCafeteria.getBusinessName()+ currCafeteria.getManager(), Toast.LENGTH_SHORT).show();

        } else{
            Log.e("Choser","009");
        }

        Log.e("choser","002");
        drawerLayout = findViewById(R.id.my_drawer_layout);
        recFood = findViewById(R.id.recFood);
        image_chosen=findViewById(R.id.cafeteria_chosen);
        txtChosen=findViewById(R.id.txtCafeteriaChosen);
//        image_chosen.setImageResource(R.drawable.left_arrow);
        txtChosen.setText(currCafeteria.getBusinessName());

        Log.d("ViewCafeteriaFood", "recFood is null: " + (recFood == null));
        Log.d("currCafeteria", "recFood is null: " + (currCafeteria.getBusinessName() == null));
        Log.d("ViewCafeteriaFood", "recFood: " + (recFood == null));

        Log.e("choser","003");
//        setupViews();
        addTypeOfFood();

        Log.e("choser","007");




    }

    private void setupPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }



    private void addTypeOfFood() {
        Log.e("choser","003");

        Items drink=new Items(R.drawable.drink,"Drinks");
        Items meal=new Items(R.drawable.meal,"Meals");
        Items sandwich =new Items(R.drawable.sandwich,"Sandwiches");

        types.add(drink);
        types.add(meal);
        types.add(sandwich);
        Log.e("choser","004");

        FoodType_Adapter fadapter = new FoodType_Adapter(this, types, this);
        Log.e("choser","005");

        if (recFood != null) {
            Log.e("choser","006");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recFood.setLayoutManager(linearLayoutManager);
            recFood.setAdapter(fadapter);
            Log.e("choser","007");

        } else {
            Log.e("ViewCafeteriaFood", "recFood is null");
        }
    }



    private void setupViews() {


    }


    @Override
    public void onItemClick(int pos) {

        Items currItem = types.get(pos);
        String itemString = gson.toJson(currItem);
        editor.putString("ITEM", itemString);
        Toast.makeText(this, prefs.getString("ITEM",""), Toast.LENGTH_SHORT).show();
        editor.commit();

        Intent intent = new Intent( ViewCafeteriaFood.this,Foods.class);
        startActivity(intent);


    }
}