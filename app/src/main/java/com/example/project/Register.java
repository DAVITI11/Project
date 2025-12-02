package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.ui.login.LoginFragment;

public class Register extends Fragment {

    Button registerButton;
    Button GoToLoginLayout;
    EditText username;
    EditText password;
    EditText firstname;
    EditText lastname;
    EditText email;
    EditText number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        registerButton = view.findViewById(R.id.registerButton);
        GoToLoginLayout = view.findViewById(R.id.GoToLoginlayout);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        firstname = view.findViewById(R.id.Firstname);
        lastname = view.findViewById(R.id.Lastname);
        email = view.findViewById(R.id.Email);
        number = view.findViewById(R.id.Number);

        registerButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Register Button Clicked", Toast.LENGTH_SHORT).show();
        });

        GoToLoginLayout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Go To Login Layout Clicked", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).ChangeFragment(new LoginFragment());
        });
    }
}