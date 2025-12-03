package com.example.project.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.DateBaseHelper;
import com.example.project.MainActivity;
import com.example.project.Register;
import com.example.project.databinding.FragmentLoginBinding;

import com.example.project.R;

public class LoginFragment extends Fragment {

    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v->{
            String user = username.getText().toString();
            String pass = password.getText().toString();
            if(!user.isEmpty() && !pass.isEmpty()){

                DateBaseHelper dbHelper = new DateBaseHelper(this.getContext());

                if (dbHelper.CheckUser(user,pass)) {
                    Toast.makeText(getContext(), "User exists!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "User NOT found!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        registerButton.setOnClickListener(v->{
            Toast.makeText(getContext(), "Register Button Clicked", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).ChangeFragment(new Register());
        });



    }
}