package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientFrmg extends Fragment {
    TextView SedanId, SUVId, CoupeId, PickupId, SportId, VanId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clientlyt, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SedanId = view.findViewById(R.id.SedanId);
        SUVId = view.findViewById(R.id.SUVId);
        CoupeId = view.findViewById(R.id.CoupeId);
        PickupId = view.findViewById(R.id.PickupId);
        SportId = view.findViewById(R.id.SportId);
        VanId = view.findViewById(R.id.VanId);
        SedanId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Sedan clicked", Toast.LENGTH_SHORT).show();
        });
        SUVId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "SUV clicked", Toast.LENGTH_SHORT).show();
        });
        CoupeId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Coupe clicked", Toast.LENGTH_SHORT).show();
        });
        PickupId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Pickup clicked", Toast.LENGTH_SHORT).show();
        });
        SportId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Sport clicked", Toast.LENGTH_SHORT).show();
        });
        VanId.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Van clicked", Toast.LENGTH_SHORT).show();
        });



        BottomNavigationView bottomNav = view.findViewById(R.id.bottomNav);
        int id = bottomNav.getSelectedItemId();

        bottomNav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Toast.makeText(getContext(), "Home clicked", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (itemId == R.id.nav_saved) {
                Toast.makeText(getContext(), "Saved clicked", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (itemId == R.id.nav_sell) {
                Toast.makeText(getContext(), "Sell clicked", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (itemId == R.id.nav_profile) {
                Toast.makeText(getContext(), "Profile clicked", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });


        //bottomNav.setSelectedItemId(R.id.nav_home);
    }

}
