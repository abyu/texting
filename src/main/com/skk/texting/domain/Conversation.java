package com.skk.texting.domain;

import java.util.ArrayList;

public class Conversation {
    private ArrayList<TextMessage> messages;

    public Conversation(ArrayList<TextMessage> messages) {

        this.messages = messages;
    }

    public ArrayList<TextMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<TextMessage> messages) {
        this.messages = messages;
    }
}
