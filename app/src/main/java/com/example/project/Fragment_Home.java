package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    TextView Sedan, SUV, Coupe, Pickup, Sport, Van;
    ListView lstv;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Sedan = view.findViewById(R.id.SedanId);
        SUV = view.findViewById(R.id.SUVId);
        Coupe = view.findViewById(R.id.CoupeId);
        Pickup = view.findViewById(R.id.PickupId);
        Sport = view.findViewById(R.id.SportId);
        Van = view.findViewById(R.id.VanId);
        lstv = view.findViewById(R.id.listView);

        Sedan.setOnClickListener(v->{Toast.makeText(requireContext(), "Sedan", Toast.LENGTH_SHORT).show();});

        SUV.setOnClickListener(v->{Toast.makeText(requireContext(), "SUV", Toast.LENGTH_SHORT).show();});

        Coupe.setOnClickListener(v-> {Toast.makeText(requireContext(), "Coupe", Toast.LENGTH_SHORT).show();});

        Pickup.setOnClickListener(v->{Toast.makeText(requireContext(), "Pickup", Toast.LENGTH_SHORT).show();});

        Sport.setOnClickListener(v-> {Toast.makeText(requireContext(), "Sport", Toast.LENGTH_SHORT).show();});

        Van.setOnClickListener(v-> {Toast.makeText(requireContext(), "Van", Toast.LENGTH_SHORT).show();});

        ArrayList<Car> carList = new ArrayList<>();
        CarAdapter adapter;
        adapter = new CarAdapter(requireContext(), carList);
        lstv.setAdapter(adapter);
        ((MainActivity)getActivity()).getCarInfo(cars -> {
            carList.clear();
            carList.addAll(cars);
            adapter.notifyDataSetChanged();
        });
        if(carList.isEmpty()){
            Toast.makeText(requireContext(), "No Cars Found", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();

    }
}
