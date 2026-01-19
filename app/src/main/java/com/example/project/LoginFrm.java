package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
                if(userName.equals("Admin") && password.equals("Admin")){
//                    SharedPreferences prefs = getActivity().getSharedPreferences("MyApp", MODE_PRIVATE);
//                    prefs.edit().putBoolean("isLoggedIn", true).putString("userName", userName).apply();
                    ((MainActivity)getActivity()).ChangeFragment(new OwnerFrmg());
                } else if (((MainActivity) getActivity()).CheckUser(password, userName)) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//                    SharedPreferences prefs = getActivity().getSharedPreferences("MyApp", MODE_PRIVATE);
//                    prefs.edit().putBoolean("isLoggedIn", true).putString("userName", userName).apply();
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

