package com.example.deliveryapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliveryapp.models.Order;
import com.example.deliveryapp.models.item;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<Order> data;
    public MyAdapter(Context c, ArrayList<Order> data) {
        this.c=c;
        this.data=data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(c);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView type = view.findViewById(R.id.my_type);
        TextView date = view.findViewById(R.id.date_now);
        TextView note = view.findViewById(R.id.edit_note);
        ImageView image = view.findViewById(R.id.image_item);
        TextView status = view.findViewById(R.id.text_status);

        Order order = data.get(i);

        type.setText(order.getType());
        date.setText(order.getTime());
        note.setText(order.getFoodName());
        image.setImageResource(R.drawable.meal); // Assuming this is a placeholder
        status.setText(order.getStatus());

        return view;
    }

}

