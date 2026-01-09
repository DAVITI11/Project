package com.example.project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatAdpt extends BaseAdapter {
    private Context context;
    private List<Message> message;
    public ChatAdpt(Context context, List<Message> message) {
        this.context = context;
        this.message = message;
    }
    @Override
    public int getCount() {
        return message.size();
    }
    @Override
    public Object getItem(int position) {
        return message.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = message.get(position);
        if (msg.isMe) {
            convertView = View.inflate(context, R.layout.item1, null);
            TextView txt = convertView.findViewById(R.id.TxtV);
            txt.setText(msg.Txt);
            } else {
            convertView = View.inflate(context, R.layout.item2, null);
            TextView txt = convertView.findViewById(R.id.TxtV);
            txt.setText(msg.Txt);
        }
        return convertView;
    }
}
