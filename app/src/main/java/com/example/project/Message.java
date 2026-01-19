package com.example.project;

public class Message {

    public String sender;
    public String receiver;
    public String message;
    public String timestamp;

    public Message(String sender, String receiver, String message, String timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }
}
