package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ClientFrmg extends Fragment {
    ListView lstv;
    EditText EdTxt;
    ImageButton ImBt;
    List<Message> lst;
    ChatAdpt adpt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clientlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstv = view.findViewById(R.id.listView);
        EdTxt = view.findViewById(R.id.etMessage);
        ImBt = view.findViewById(R.id.btnSend);

        lst = new ArrayList<>();
        adpt = new ChatAdpt(getContext(), lst);
        lstv.setAdapter(adpt);

        ImBt.setOnClickListener(v->{
            String msg = EdTxt.getText().toString();
            if(!msg.isEmpty()){
                lst.add(new Message(msg, true));
                adpt.notifyDataSetChanged();
                lstv.setSelection(lst.size()-1);
                EdTxt.setText("");
            }
        });
    }
}
