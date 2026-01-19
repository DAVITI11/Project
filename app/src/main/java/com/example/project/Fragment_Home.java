package com.example.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
    TextView All,Sedan, SUV, Coupe, Pickup, Sport, Van;
    ListView lstv;
    ArrayList<Car> carList;
    CarAdapter adapter;

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
        All = view.findViewById(R.id.AllId);


        carList = new ArrayList<>();
        adapter = new CarAdapter(requireContext(), carList);
        lstv.setAdapter(adapter);
        temp();
        if(carList.isEmpty()){
            Toast.makeText(requireContext(), "No Cars Found", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();

        lstv.setOnItemLongClickListener((parent, view1, position, id) -> {

            Car selectedCar = (Car) parent.getItemAtPosition(position);

            showOptionsDialog(selectedCar);

            return true;
        });
        All.setOnClickListener(v->{Toast.makeText(requireContext(), "All", Toast.LENGTH_SHORT).show();adapter.filterByTp("!");});

        Sedan.setOnClickListener(v->{Toast.makeText(requireContext(), "Sedan", Toast.LENGTH_SHORT).show();adapter.filterByTp("Sedan");});

        SUV.setOnClickListener(v->{Toast.makeText(requireContext(), "SUV", Toast.LENGTH_SHORT).show();adapter.filterByTp("SUV");});

        Coupe.setOnClickListener(v-> {Toast.makeText(requireContext(), "Coupe", Toast.LENGTH_SHORT).show();adapter.filterByTp("Coupe");});

        Pickup.setOnClickListener(v->{Toast.makeText(requireContext(), "Pickup", Toast.LENGTH_SHORT).show();adapter.filterByTp("Pickup");});

        Sport.setOnClickListener(v-> {Toast.makeText(requireContext(), "Sport", Toast.LENGTH_SHORT).show();adapter.filterByTp("Sport");});

        Van.setOnClickListener(v-> {Toast.makeText(requireContext(), "Van", Toast.LENGTH_SHORT).show();adapter.filterByTp("Van");temp();});
    }
    void temp(){
        ((MainActivity)getActivity()).getCarInfo((cars) -> {
            carList.clear();
            carList.addAll(cars);
            adapter.notifyDataSetChanged();
        });
        adapter.filterByTp("!");
    }
    private void showOptionsDialog(Car item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle("Select Action")
                .setItems(new CharSequence[]{"Call", "Save", "Send Message"}, (dialog, which) -> {

                    switch (which) {
                        case 0:
                            callNumber("555416550");
                            break;

                        case 1:
                           // saveItem(item);
                            break;

                        case 2:
                            ((MainActivity)getActivity()).ChangeFragment(new ChatFrgm());
                            break;
                    }
                });

        builder.show();
    }
    private void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }


}
