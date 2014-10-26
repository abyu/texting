package com.skk.texting;

import android.app.Application;
import com.skk.texting.domain.Conversation;

public class TextingApplication extends Application {
    private Conversation currentConversation;

    public Conversation getCurrentConversation() {
        return currentConversation;
    }

    public void setCurrentConversation(Conversation currentConversation) {
        this.currentConversation = currentConversation;
    }
}
