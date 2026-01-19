package com.example.project;

import android.graphics.Bitmap;

public class Car {
    public String carType;
    public String model;
    public String year;
    public String price;
    public String description;
    public Bitmap image;
    public String owner;

    public Car(String carType, String model, String year, String price, String description, Bitmap image,String owner) {
        this.carType = "Car Type: " + carType;
        this.model = "Car Model: " + model;
        this.year = "Year: " + year;
        this.price = "Price:" + price;
        this.description = "Description: " + description;
        this.image = image;
        this.owner = owner;
    }
}
