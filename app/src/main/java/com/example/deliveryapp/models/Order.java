package com.example.deliveryapp.models;

public class Order {
    private  int orderID;
    private int userID;
    private String foodName;
    private String cost;
    private String time;
    private int business;
    private String type;

    private String status;
    public Order(){}


    public Order(int orderID, int userID, String foodName, String cost, String time, int business, String type,String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.foodName = foodName;
        this.cost = cost;
        this.time = time;
        this.business = business;
        this.type = type;
        this.status=status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
