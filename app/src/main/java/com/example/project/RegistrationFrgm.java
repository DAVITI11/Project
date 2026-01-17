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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class RegistrationFrgm extends Fragment {
    Button Reg,GotoLogin;
    String userName, password, firstNm,lasNm,email, address;
    EditText UserNm, Pass,FirstNm,LastNm, eml, adrs;
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
        FirstNm = view.findViewById(R.id.FirstName);
        LastNm = view.findViewById(R.id.LastName);
        eml = view.findViewById(R.id.email);
        adrs = view.findViewById(R.id.address);
        Reg = view.findViewById(R.id.RegBtn);
        GotoLogin = view.findViewById(R.id.GotoLogin);

        GotoLogin.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Go Back Login")
                    .setMessage("Are you sure you want to go back to login")
                    .setPositiveButton(("Yes"), (dialog, which) -> {
                        ((MainActivity)getActivity()).ChangeFragment(new LoginFrm());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        Reg.setOnClickListener(v->{
//            userName = UserNm.getText().toString();
//            password = Pass.getText().toString();
//            email = eml.getText().toString();
//            address = adrs.getText().toString();
//            if(!userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !address.isEmpty()){
//                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(requireContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
//            }
            userName = UserNm.getText().toString();
            password = Pass.getText().toString();

            if(!userName.isEmpty() && !password.isEmpty()){
                ((MainActivity)getActivity()).addUserToServer(userName, password);
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(requireContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
