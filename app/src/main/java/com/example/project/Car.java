package com.example.project;

import android.graphics.Bitmap;

public class Car {
    public String carType;
    public String model;
    public String year;
    public String price;
    public String description;
    public Bitmap image;

    public Car(String carType, String model, String year, String price, String description, Bitmap image) {
        this.carType = "Car Type: " + carType;
        this.model = "Car Model: " + model;
        this.year = "Year: " + year;
        this.price = "Price:" + price;
        this.description = "Description: " + description;
        this.image = image;
    }
}
