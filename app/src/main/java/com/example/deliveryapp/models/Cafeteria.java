package com.example.deliveryapp.models;

import java.util.ArrayList;

public class Cafeteria {

    private String name;
    private ArrayList<Food> foods;

    private int img;

    private String description;
    private double rating;
    public Cafeteria(){

    }


    public Cafeteria(String name, int img, String desc, double rating) {
        this.name = name;
        this.rating=rating;
        this.img = img;
        this.description = desc;
        foods = new ArrayList<Food>();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
