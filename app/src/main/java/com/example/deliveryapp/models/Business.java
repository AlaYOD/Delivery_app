package com.example.deliveryapp.models;

public class Business {
    private int businessID;
    private String businessName;
    private String manager;
    private String mobile;
    private int userID;
    private String imageUrl; // URL to the image


    // Constructor
    public Business(int businessID, String businessName, String manager, String mobile, int userID) {
        this.businessID = businessID;
        this.businessName = businessName;
        this.manager = manager;
        this.mobile = mobile;
        this.userID = userID;
    }

    // Default constructor
    public Business() {
    }

    // Getters and Setters
    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // Optional: Override toString for easy printing
    @Override
    public String toString() {
        return "Business{" +
                "businessID=" + businessID +
                ", businessName='" + businessName + '\'' +
                ", manager='" + manager + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userID=" + userID +
                '}';
    }
}

