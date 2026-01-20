package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
public class Fragment_Saved extends Fragment {

    private ListView listV;
    private CarAdapter adapter;
    private ArrayList<Car> savedCars;
    private String currentUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        listV = view.findViewById(R.id.listV);

        SharedPreferences userPrefs =
                requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE);

        currentUsername = userPrefs.getString("username", null);
        if (currentUsername == null) return;

        savedCars = loadSavedCars(currentUsername);
        adapter = new CarAdapter(requireContext(), savedCars);
        listV.setAdapter(adapter);

        listV.setOnItemLongClickListener((parent, v, position, id) -> {
            showOptionsDialog(position);
            return true;
        });
    }

    private void showOptionsDialog(int position) {
        String[] options = {"Call", "Delete"};

        new AlertDialog.Builder(requireContext())
                .setTitle("Choose action")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        callOwner(savedCars.get(position));
                    } else {
                        deleteCar(position);
                    }
                })
                .show();
    }

    private void callOwner(Car car) {
        if (car.owner == null || car.owner.isEmpty()) {
            Toast.makeText(requireContext(),
                    "Phone not available",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + car.owner));
        startActivity(intent);
    }

    private void deleteCar(int position) {
        savedCars.remove(position);
        adapter.notifyDataSetChanged();
        saveUpdatedList(currentUsername);

        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }

    private void saveUpdatedList(String username) {

        SharedPreferences prefs =
                requireActivity().getSharedPreferences("SavedCars", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        prefs.edit()
                .putString("saved_" + username, gson.toJson(savedCars))
                .apply();
    }

    private ArrayList<Car> loadSavedCars(String username) {

        SharedPreferences prefs =
                requireActivity().getSharedPreferences("SavedCars", Context.MODE_PRIVATE);

        String json = prefs.getString("saved_" + username, "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Car>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
