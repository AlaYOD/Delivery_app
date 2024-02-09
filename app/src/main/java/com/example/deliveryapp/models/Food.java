package com.example.deliveryapp.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

public class Food {

    private String name;
    private String img;
    private String time;
    private double price;
    private int foodId;
    private String cafeteria;

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }




    public Food(){

    }

    public Food(String name, String time, String img, double price, String cafeteria) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.cafeteria = cafeteria;
        this.time=time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



}
