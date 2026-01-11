package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class OwnerFrmg extends Fragment {

    Button btnCht,GoBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ownerlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCht = view.findViewById(R.id.btnCht);
        GoBack = view.findViewById(R.id.GoBack);

        btnCht.setOnClickListener(v->{
            ((MainActivity)getActivity()).ChangeFragment(new ChatFrgm());
        });
        GoBack.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Go Back Login")
                    .setMessage("Are you sure you want to go back to login?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((MainActivity)getActivity()).ChangeFragment(new LoginFrm());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
