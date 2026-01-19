package com.example.project;

import android.os.Bundle;
import android.os.Handler;
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

public class ChatFrgm extends Fragment {

    ListView lstv;
    Button GoBack;
    EditText EdTxt;
    ImageButton ImBt;

    ArrayList<Message> lst;
    ChatAdpt adpt;

    Handler handler = new Handler();
    Runnable refreshTask;

    MainActivity act;

    String currentUser;
    String chatWithUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chatlyt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        act = (MainActivity) getActivity();
        currentUser = act.getUsNm();

        lstv = view.findViewById(R.id.listView);
        EdTxt = view.findViewById(R.id.etMessage);
        ImBt = view.findViewById(R.id.btnSend);
        GoBack = view.findViewById(R.id.GoBack);

        lst = new ArrayList<com.example.project.Message>();
        adpt = new ChatAdpt(requireContext(), lst);
        lstv.setAdapter(adpt);

        ImBt.setOnClickListener(v -> {
            String text = EdTxt.getText().toString();

            if (!text.isEmpty()) {
              //  lst.add(new Message(text, true));
                adpt.notifyDataSetChanged();
                lstv.setSelection(lst.size() - 1);
                EdTxt.setText("");
            }
        });


        // SEND MESSAGE
        ImBt.setOnClickListener(v -> {
            String msg = EdTxt.getText().toString().trim();
            if (!msg.isEmpty()) {
                act.sendMessage(currentUser, chatWithUser, msg);
                EdTxt.setText("");
                loadMessages(); // refresh immediately
            }
        });

        lstv.setOnItemLongClickListener((parent, itemView, position, id) -> {

            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Message")
                    .setMessage("Delete this message?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        lst.remove(position);
                        adpt.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });

        GoBack.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Exit Chat")
                    .setMessage("Are you sure you want to exit chat?")
                    .setPositiveButton("Yes", (dialog, which) ->
                            act.ChangeFragment(new ClientFrmg())
                    )
                    .setNegativeButton("No", null)
                    .show();
        });

        startAutoRefresh();
    }

    private void startAutoRefresh() {
        refreshTask = new Runnable() {
            @Override
            public void run() {
                loadMessages();
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(refreshTask);
    }

    private void loadMessages() {
        act.getMessages(list -> {
            lst.clear();
            lst.addAll(list);
            adpt.notifyDataSetChanged();
            lstv.setSelection(lst.size() - 1); // scroll bottom
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshTask);
    }
}
