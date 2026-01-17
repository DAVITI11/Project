package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SelectUsers extends Fragment {

    ListView listV;
    ArrayList<String> lst;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selectusers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listV = view.findViewById(R.id.list);
        lst = new ArrayList<>();

        // Get all username + password pairs from MainActivity
//        ArrayList<Pair<String,String>> infoList =
//                ((MainActivity)getActivity()).GetAllInfo();
//
//        // Convert pairs into readable strings
//        for (Pair<String, String> p : infoList) {
//            lst.add(p.first + " ----> " + p.second);
//        }
//
//        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, lst);
//        listV.setAdapter(adapter);
    }
}
