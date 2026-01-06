package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFrm extends Fragment {

    EditText UserName, Password;
    Button LoginBtn;
    String userName, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loginlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserName = view.findViewById(R.id.etUsername);
        Password = view.findViewById(R.id.etPassword);
        LoginBtn = view.findViewById(R.id.btnLogin);

        LoginBtn.setOnClickListener(v->{
            userName = UserName.getText().toString();
            password = Password.getText().toString();
            if(userName.equals("Admin") && password.equals("Admin")) {
                Toast.makeText(getContext(), "Enter Success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Enter User Name and Password", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
