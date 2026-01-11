package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class ChatFrgm extends Fragment {
    ListView lstv;
    Button GoBack;
    EditText EdTxt;
    ImageButton ImBt;
    List<Message> lst;
    ChatAdpt adpt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chatlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstv = view.findViewById(R.id.listView);
        EdTxt = view.findViewById(R.id.etMessage);
        ImBt = view.findViewById(R.id.btnSend);
        GoBack = view.findViewById(R.id.GoBack);

        //GoBack.setNavigationOnClickListener(v -> finish());

        lst = new ArrayList<>();
        adpt = new ChatAdpt(requireContext(), lst);
        lstv.setAdapter(adpt);

        GoBack.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Exit Chat")
                    .setMessage("Are you sure you want to exit chat?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ((MainActivity)getActivity()).ChangeFragment(new OwnerFrmg());
                    }).setNegativeButton("No", null).show();
        });

        ImBt.setOnClickListener(v->{
            String msg = EdTxt.getText().toString();
            if(!msg.isEmpty()){
                lst.add(new Message(msg, true));
                adpt.notifyDataSetChanged();
                lstv.setSelection(lst.size()-1);
                EdTxt.setText("");
            }
        });
        lstv.setOnItemLongClickListener((parent, itemView, position, id) -> {

            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Message")
                    .setMessage("Are you sure you want to delete this message?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        lst.remove(position);
                        adpt.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }
}
