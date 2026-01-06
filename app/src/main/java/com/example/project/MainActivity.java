package com.example.project;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {

    FrameLayout cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        cont = findViewById(R.id.cont);
    }
    public void ChangeFragment(Fragment fm){
        getSupportFragmentManager().beginTransaction().replace(R.id.cont, fm).commit();
    }
}