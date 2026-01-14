package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Sell extends Fragment {
    ImageView BtnHm,AddPhoto;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BtnHm = view.findViewById(R.id.btnHomeTop);
        AddPhoto = view.findViewById(R.id.carImage);
        BtnHm.setOnClickListener(v -> {
            ((MainActivity)getActivity()).ChangeFragment(new ClientFrmg());
        });

        Spinner spinner = view.findViewById(R.id.spinnerCars);

        String[] carsTp = {"Select Car Type","Sedan", "SUV", "Coupe", "Pickup", "Sport", "Van"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                carsTp
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        AddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 100);
        });
        ActivityResultLauncher<String> pickImageLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                    if (uri != null) {
                        AddPhoto.setImageURI(uri);
                    }
                });

        AddPhoto.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

    }
}
