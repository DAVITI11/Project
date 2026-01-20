package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Fragment_Profile extends Fragment {
    TextView profileName, profileEmail, valuePhone,valueAddress;
    Button btnEditProfile, btnLogout;
    UserInfo userInfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        valuePhone = view.findViewById(R.id.valuePhone);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);


        ((MainActivity)getActivity()).GetUserInfo(info -> {
            if(info != null){
                profileName.setText(info.firstname+ " "+ info.lastname);

                profileEmail.setText(info.Email);

                valuePhone.setText(info.Phone);

            } else {
                Toast.makeText(getActivity(), "Error loading user info", Toast.LENGTH_SHORT).show();
            }
        });


        btnLogout.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        SharedPreferences prefs = getActivity().getSharedPreferences("MyApp", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.apply();
                        ((MainActivity)getActivity()).ChangeFragment(new LoginFrm());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        btnEditProfile.setOnClickListener(v->{
            ((MainActivity)getActivity()).ChangeFragment(new EditProfileFrm());
        });
    }
}
