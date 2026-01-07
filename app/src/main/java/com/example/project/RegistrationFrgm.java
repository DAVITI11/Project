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


public class RegistrationFrgm extends Fragment {
    Button Reg,GotoLogin;
    String userName, password, email, address;
    EditText UserNm, Pass, eml, adrs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registrationlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        UserNm = view.findViewById(R.id.UserNm);
        Pass = view.findViewById(R.id.Pass);
        eml = view.findViewById(R.id.email);
        adrs = view.findViewById(R.id.address);
        Reg = view.findViewById(R.id.RegBtn);
        GotoLogin = view.findViewById(R.id.GotoLogin);
        GotoLogin.setOnClickListener(v->{
            ((MainActivity)getActivity()).ChangeFragment(new LoginFrm());
        });
    }
}
