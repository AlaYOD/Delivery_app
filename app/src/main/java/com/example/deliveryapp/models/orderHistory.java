package com.example.deliveryapp.models;

public class orderHistory {
   private String FoodName;
   private String Date;
   private double Cost;

    public orderHistory(String foodName, String date, double cost) {
        FoodName = foodName;
        Date = date;
        Cost = cost;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }
}
