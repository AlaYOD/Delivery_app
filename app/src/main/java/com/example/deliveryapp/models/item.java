package com.example.deliveryapp.models;


public class item {
    private String type;
    private String date;
    private String note;
    private String status;
    private int imageResource;



    public item(String type, String date, String note, String status,int imageResource) {
        this.type = type;
        this.date = date;
        this.note = note;
        this.status=status;
        this.imageResource = imageResource;

    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}

