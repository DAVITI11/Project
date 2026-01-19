package com.example.project;

import java.util.ArrayList;

public interface ChatCallback {
    void onMessagesLoaded(ArrayList<Message> messages);
}
