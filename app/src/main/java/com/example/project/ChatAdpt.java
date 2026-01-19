package com.example.project;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdpt extends BaseAdapter {

    private final Context context;
    private final List<Message> messages;

    public ChatAdpt(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = messages.get(position);
        String usNm = ((MainActivity) context).getUsNm();
        if (msg.sender.equals(usNm)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message msg = messages.get(position);
        int type = getItemViewType(position);

        if (convertView == null) {
            if (type == 0) {
                convertView = View.inflate(context, R.layout.item1, null);
            } else {
                convertView = View.inflate(context, R.layout.item2, null);
            }
        }

        TextView txt = convertView.findViewById(R.id.TxtV);
        txt.setText(msg.message);

        return convertView;
    }
}
