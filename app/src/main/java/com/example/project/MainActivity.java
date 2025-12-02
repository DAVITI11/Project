package com.example.project;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    FrameLayout cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cont = findViewById(R.id.cont);


            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.cont, new LoginFragment()).commit();

    }

    public void ChangeFragment(Fragment fm){
        getSupportFragmentManager().beginTransaction().replace(R.id.cont, fm).commit();
    }
}