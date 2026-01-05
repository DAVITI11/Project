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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Register extends Fragment {

    Button registerButton,GoToLoginLayout;
    EditText username,password,firstname,lastname,email,number;
    DataBaseHelper dbHelper;
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
            String user = username.getText().toString();
            String pass = password.getText().toString();
            String first = firstname.getText().toString();
            String last = lastname.getText().toString();
            String mail = email.getText().toString();
            String num = number.getText().toString();
            if(!user.isEmpty() && !pass.isEmpty() && !first.isEmpty() && !last.isEmpty() && !mail.isEmpty() && !num.isEmpty()){
                dbHelper = new DataBaseHelper(getContext());
                dbHelper.AddNewUser(first, last, mail, num);
                dbHelper.AddUser(user, pass);
                Toast.makeText(getContext(), "User Registered!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(getContext(), "djndj", Toast.LENGTH_SHORT).show();
        });
        GoToLoginLayout.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("⚠\uFE0F")
                    .setMessage("Are you sure you want to go to login layout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((MainActivity) getActivity()).ChangeFragment(new LoginFragment());
                    }).setNegativeButton("No",(dialog, which) ->{
                        dialog.dismiss();
                    } ).show();
        });
    }
}