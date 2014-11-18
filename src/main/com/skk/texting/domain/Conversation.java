package com.skk.texting.domain;

import android.database.Cursor;

import java.util.ArrayList;

public class Conversation {
    private ArrayList<TextMessage> messages;
    private Cursor cursorEntity;
    private Person recipient;
    private String threadId;

    public Conversation(Cursor conversationCursor) {
        cursorEntity = conversationCursor;
    }

    public Conversation() {

    }

    public Cursor getCursorEntity() {
        return cursorEntity;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public TextMessage getMessage(Cursor cursor) {
        return TextMessage.fromCursor(cursor);
    }

    public String getRecipientAddress() {
        return recipient.getAddress();
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadId() {
        return threadId;
    }

}
