package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class EditProfileFrm extends Fragment {
    Button btnSave, GoBackToProfile;
    EditText etPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editprofilelyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btnSave);
        GoBackToProfile = view.findViewById(R.id.GoBackToProfile);
        etPassword = view.findViewById(R.id.etPassword);

        btnSave.setOnClickListener(v->{

        });
        GoBackToProfile.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Go Back")
                    .setMessage("Are you sure you want to go back?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((MainActivity)getActivity()).ChangeFragment(new ClientFrmg());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
