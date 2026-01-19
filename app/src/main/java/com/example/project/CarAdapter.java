package com.example.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Car;
import com.example.project.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
public class CarAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Car> originalList;   // full list
    private ArrayList<Car> filteredList;   // filtered list
    private LayoutInflater inflater;

    public CarAdapter(Context context, ArrayList<Car> carList) {
        this.context = context;
        this.originalList = carList;
        this.filteredList = new ArrayList<>(carList);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filterByTp(String tp) {
        filteredList.clear();

        if(tp.equals("!")) {
            filteredList.addAll(originalList); // show all
        } else {
            for(Car c : originalList) {
                if(c.carType.substring(10).equals(tp)) {
                    filteredList.add(c);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.car_item, parent, false);

            holder = new ViewHolder();
            holder.carImage = convertView.findViewById(R.id.carImage);
            holder.modelText = convertView.findViewById(R.id.carModel);
            holder.priceText = convertView.findViewById(R.id.carPrice);
            holder.yearText = convertView.findViewById(R.id.carYear);
            holder.descText = convertView.findViewById(R.id.carDesc);
            holder.typeText = convertView.findViewById(R.id.carType);
            holder.ownerText = convertView.findViewById(R.id.owner);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Car car = filteredList.get(position);

        holder.modelText.setText(car.model);
        holder.priceText.setText(car.price);
        holder.yearText.setText(car.year);
        holder.descText.setText(car.description);
        holder.typeText.setText(car.carType);
        holder.ownerText.setText(car.owner);

        // Load image from file path
        File imgFile = new File(car.image);

        if (imgFile.exists()) {
            holder.carImage.setImageURI(Uri.fromFile(imgFile));
        } else {
            holder.carImage.setImageResource(R.drawable.ic_add_photo);
        }

        return convertView;
    }


    private static class ViewHolder {
        ImageView carImage;
        TextView modelText;
        TextView priceText;
        TextView yearText;
        TextView descText;
        TextView typeText;
        TextView ownerText;
    }
}
