package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientFrmg extends Fragment {
    FrameLayout contentFrame;
    ImageView btnHomeTop;
    TextView HeadTxt;
    BottomNavigationView bottomNav;
    String HeadTxtStr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clientlyt, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contentFrame = view.findViewById(R.id.contentFrame);
        btnHomeTop = view.findViewById(R.id.btnHomeTop);
        HeadTxt = view.findViewById(R.id.HeadTxt);

        btnHomeTop.setOnClickListener(v->{
            ChangeFrm(new Fragment_Home());
            HeadTxtStr = "Online Shop";
            HeadTxt.setText(HeadTxtStr);
            bottomNav.setSelectedItemId(R.id.nav_home);
        });

        ChangeFrm(new Fragment_Home());

        bottomNav = view.findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {

            Fragment selected = null;

            if (item.getItemId() == R.id.nav_home)  {selected = new Fragment_Home();HeadTxtStr = "Online Shop";}
            if (item.getItemId() == R.id.nav_saved) {selected = new Fragment_Saved();HeadTxtStr = "Saved Page";}
            if (item.getItemId() == R.id.nav_sell)  {selected = new Fragment_Sell();HeadTxtStr = "Sell Page";}
            if (item.getItemId() == R.id.nav_profile) {selected = new Fragment_Profile();HeadTxtStr = "Profile Page";}

            if (selected != null) {
                HeadTxt.setText(HeadTxtStr);
                ChangeFrm(selected);
            }

            return true;
        });
    }
    public void ChangeFrm(Fragment fm){
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fm)
                .commit();
    }

}
