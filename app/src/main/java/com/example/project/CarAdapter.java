package com.example.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Car;
import com.example.project.R;

import java.util.ArrayList;

public class CarAdapter extends BaseAdapter {

    Context context;
    ArrayList<Car> carList;

    public CarAdapter(Context context, ArrayList<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        }

        ImageView carImage = convertView.findViewById(R.id.carImage);
        TextView modelText = convertView.findViewById(R.id.carModel);
        TextView priceText = convertView.findViewById(R.id.carPrice);

        Car car = carList.get(position);

        modelText.setText(car.model);
        priceText.setText(car.price);

        if (car.image != null) {
            carImage.setImageBitmap(car.image);
        }

        return convertView;
    }
}
