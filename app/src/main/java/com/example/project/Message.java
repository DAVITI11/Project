package com.example.project;

public class Message {
    public String Txt;
    public boolean isMe;

    public Message(String txt, boolean isMe) {
        this.Txt = txt;
        this.isMe = isMe;
    }
    public String getTxt() {
        return Txt;
    }

    public void setTxt(String txt) {
        Txt = txt;
    }
    public boolean isMe() {
        return isMe;
    }
}
