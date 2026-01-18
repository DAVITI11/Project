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
        this.carType = carType;
        this.model = model;
        this.year = year;
        this.price = price;
        this.description = description;
        this.image = image;
    }
}
