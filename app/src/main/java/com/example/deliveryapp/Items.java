package com.example.deliveryapp;

public class Items {

    private int imageResource;
    private String title;

    public Items(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

}