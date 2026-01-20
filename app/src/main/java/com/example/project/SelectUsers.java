package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class SelectUsers extends Fragment {

    ListView listV;
    ArrayList<String> lst;
    ArrayAdapter<String> adapter;
    Button back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selectusers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listV = view.findViewById(R.id.listV);
        back = view.findViewById(R.id.Back);


        lst = new ArrayList<>();

        for (Pair<String, String> p : ((MainActivity) getActivity()).NamePass)
            lst.add("name: " + p.second + "\npassword: " +p.first);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, lst);
        listV.setAdapter(adapter);

        back.setOnClickListener(v->{
            ((MainActivity)getActivity()).ChangeFragment(new OwnerFrmg());
        });
        listV.setOnItemClickListener((parent, view1, position, id) -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("do you want to chat with this user?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        ((MainActivity)getActivity()).ChangeFragment(new ChatFrgm());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
