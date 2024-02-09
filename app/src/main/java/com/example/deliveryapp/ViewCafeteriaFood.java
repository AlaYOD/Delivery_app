package com.example.deliveryapp;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Cafeteria;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class ViewCafeteriaFood extends AppCompatActivity implements RecyclerViewInterface {

    private DrawerLayout drawerLayout;
   private ImageView image_chosen;
   private TextView txtChosen;
    private RecyclerView recFood;
    Cafeteria  currCafeteria;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private boolean flag = false;
    public static final String FLAG = "FLAG";

    private ArrayList<Items> types = new ArrayList<Items>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.foods_layout);
        setupPrefs();
       // Toast.makeText(this, prefs.getString("Cafeteria1",""), Toast.LENGTH_SHORT).show();
        String str = prefs.getString("Cafeteria1","");
        if(str!=null) {

            currCafeteria = gson.fromJson(str, Cafeteria.class);

            // Toast.makeText(this, currCafeteria.getName()+ currCafeteria.getImg(), Toast.LENGTH_SHORT).show();

        }
        setupViews();
        addTypeOfFood();






    }

    private void setupPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        editor = prefs.edit();
    }



    private void addTypeOfFood() {

        Items drink=new Items(R.drawable.drink,"Drinks");
        Items meal=new Items(R.drawable.meal,"Meals");
        Items sandwich =new Items(R.drawable.sandwich,"Sandwiches");

        types.add(drink);
        types.add(meal);
        types.add(sandwich);

        FoodType_Adapter fadapter = new FoodType_Adapter(this, types, this);

        if (recFood != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recFood.setLayoutManager(linearLayoutManager);
            recFood.setAdapter(fadapter);
        } else {
            Log.e("ViewCafeteriaFood", "recFood is null");
        }}



    private void setupViews() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        recFood = findViewById(R.id.recFood);
        image_chosen=findViewById(R.id.cafeteria_chosen);
        txtChosen=findViewById(R.id.txtCafeteriaChosen);
        image_chosen.setImageResource(currCafeteria.getImg());
        txtChosen.setText(currCafeteria.getName());
        Log.d("ViewCafeteriaFood", "recFood is null: " + (recFood == null));
    }


    @Override
    public void onItemClick(int pos) {

        Items currItem = types.get(pos);
        currItem.setId(currCafeteria.getId());
        String itemString = gson.toJson(currItem);
        editor.putString("ITEM", itemString);
        Toast.makeText(this, prefs.getString("ITEM",""), Toast.LENGTH_SHORT).show();
        editor.commit();

          Intent intent = new Intent( ViewCafeteriaFood.this,Foods.class);
         startActivity(intent);


    }
}

