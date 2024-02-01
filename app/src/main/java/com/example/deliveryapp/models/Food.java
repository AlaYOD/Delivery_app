package com.example.deliveryapp.models;

public class Food {

    private String name;
    private String desc;
    private int img;
    private double price;
    private String cafeteria;


    public Food(){

    }

    public Food(String name, String desc, int img, double price, String cafeteria) {
        this.name = name;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.cafeteria = cafeteria;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHotel() {
        return cafeteria;
    }

    public void setHotel(String hotel) {
        this.cafeteria = hotel;
    }


}
