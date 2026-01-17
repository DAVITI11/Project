package com.example.project;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoginFrm extends Fragment {

    EditText UserName, Password;
    Button LoginBtn,RegBtn;
    String userName, password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loginlyt, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserName = view.findViewById(R.id.Username);
        Password = view.findViewById(R.id.Password);
        LoginBtn = view.findViewById(R.id.btnLogin);
        RegBtn = view.findViewById(R.id.RegBtn);

        RegBtn.setOnClickListener(v->{
            ((MainActivity)getActivity()).ChangeFragment(new RegistrationFrgm());
        });

        LoginBtn.setOnClickListener(v->{
            userName = UserName.getText().toString();
            password = Password.getText().toString();
            if(!userName.isEmpty() && !password.isEmpty()) {
                if (((MainActivity) getActivity()).CheckUser(password, userName)) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).ChangeFragment(new ClientFrmg());
                }else{
                    Toast.makeText(getContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
