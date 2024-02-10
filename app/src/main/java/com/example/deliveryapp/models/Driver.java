package com.example.deliveryapp.models;

public class Driver {
    private String name;
    private String mobile;
    private String sn;
    public Driver() {
        // Default constructor required for calls to DataSnapshot.getValue(Driver.class)
    }
    public Driver(String name, String mobile, String sn) {
        this.name = name;
        this.mobile = mobile;
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
