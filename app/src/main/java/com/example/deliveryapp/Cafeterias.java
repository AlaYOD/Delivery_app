package com.example.deliveryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp.models.Cafeteria;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Cafeterias extends AppCompatActivity implements RecyclerViewInterface {

    private DrawerLayout drawerLayout;

    private RecyclerView recCafeteria;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    private boolean flag = false;
    // public static final String FLAG = "FLAG";

    private ArrayList<Cafeteria> cafeterias = new ArrayList<Cafeteria>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria);
        setupPrefs();

        setupViews();
        addCafeteria();

    }

    private void setupPrefs() {
        prefs = getSharedPreferences("DATA", MODE_PRIVATE);
        //prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }



    private void addCafeteria() {
        Cafeteria vanila = new Cafeteria("vanaila", R.drawable.vanila, "best for sweet drinks", 5,1);
        Cafeteria maramia = new Cafeteria("Maramia", R.drawable.maramia, "sweet drinks", 3,3);
        Cafeteria burger = new Cafeteria("Burger", R.drawable.burger, "meet", 2,2);

        Log.d("vanila",vanila.getDesc());
        cafeterias.add(burger);
        cafeterias.add(vanila);
        cafeterias.add(maramia);

        Cafeteria_Adapter Cadapter = new Cafeteria_Adapter(this, cafeterias, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recCafeteria.setLayoutManager(linearLayoutManager);
        recCafeteria.setAdapter(Cadapter);


    }



    private void setupViews() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        recCafeteria = findViewById(R.id.recCafeteria);

    }


    @Override
    public void onItemClick(int pos) {

        Cafeteria currCafeteria = cafeterias.get(pos);
        String cafeteriaString = gson.toJson(currCafeteria);
        //   Toast.makeText(this,     prefs.getString("Cafeteria1",""), Toast.LENGTH_SHORT).show();
        editor.putString("Cafeteria1", cafeteriaString);

        editor.commit();
        // Toast.makeText(this,     prefs.getString("Cafeteria1",""), Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(Cafeterias.this, ViewCafeteriaFood.class);
        startActivity(intent);


    }
}