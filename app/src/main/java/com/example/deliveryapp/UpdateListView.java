package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.deliveryapp.models.Order;
import com.example.deliveryapp.models.item;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UpdateListView extends AppCompatActivity {

    ExtendedFloatingActionButton btn_save;
    Spinner spinnerStatus;
    Button updateButton;

    private String selectedSpinnerItemStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list_view);



        spinnerStatus = findViewById(R.id.update_spinner_status);
        updateButton = findViewById(R.id.update_button);

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpinnerItemStatus = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        updateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = getIntent();
//                int position = intent.getIntExtra("position", -1);
//
//
//                if(position != -1) {
//                    //public item(String type, String date, String note, String status,int imageResource)
//                    Order clickedItem = cartView.data.get(position);
//                    Order updated_item = new Order(clickedItem.getType(),clickedItem.getDate(),clickedItem.getNote(),selectedSpinnerItemStatus.toString(),clickedItem.getImageResource());
//
////               ssssssssssssssss
//
//                    SharedPreferences prefs = getSharedPreferences(cartView.DATA, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = prefs.edit();
//
//                    String strLength = prefs.getString(cartView.ARRAY_LENGTH, "");
//
//                    int length = 0;
//                    try {
//                        length = Integer.parseInt(strLength);
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (position >= 0 && position < length) {
//                        // Retrieve the existing list of items
//                        List<item> itemList = new ArrayList<>();
//                        for (int i = 0; i < length; i++) {
//                            String str = prefs.getString(String.valueOf(i), "");
//                            if (!str.isEmpty()) {
//                                item array_item = new Gson().fromJson(str, item.class);
//                                itemList.add(array_item);
//                            }
//                        }
//
//
//
//                        itemList.set(position, updated_item);
//                        editor.clear();
//
//                        for (int i = 0; i < itemList.size(); i++) {
//                            String itemString = new Gson().toJson(itemList.get(i));
//                            editor.putString(String.valueOf(i), itemString);
//                        }
//
//                        editor.putString(cartView.ARRAY_LENGTH, String.valueOf(itemList.size()));
//                        editor.apply();
//
//                        Intent intent2 = new Intent(UpdateListView.this, MainActivity.class);
//                        startActivity(intent2);
//
//
//
//                    }
//
//                }
//            }
//        });




    }


}