package com.skk.texting.viewwrapper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.inject.Inject;
import com.skk.texting.evented.Event;
import com.skk.texting.evented.EventRepository;
import com.skk.texting.R;
import com.skk.texting.TextingApplication;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.TextMessage;
import com.skk.texting.eventdata.RepliedSms;

public class MessageConsoleWrapper implements View.OnClickListener {

    private EditText replyText;
    private ConversationRepository conversationRepository;
    private EventRepository eventRepository;
    private Button replyButton;

    @Inject
    public MessageConsoleWrapper(ConversationRepository conversationRepository, EventRepository eventRepository) {

        this.conversationRepository = conversationRepository;
        this.eventRepository = eventRepository;
    }

    public void initialize(View messageConsole) {
        replyButton = (Button) messageConsole.findViewById(R.id.reply);
        replyText = (EditText) messageConsole.findViewById(R.id.reply_text);

        replyButton.setOnClickListener(new ReplyButtonEventHandler());
        replyText.addTextChangedListener(new ReplyTextEventHandler());

    }

    public void replyButtonOnClick(View view) {
        TextingApplication applicationContext = (TextingApplication) view.getContext().getApplicationContext();

        TextMessage replyMessage = new TextMessage(replyText.getText().toString());
        String messageText = replyMessage.getMessageText();
        Conversation currentConversation = applicationContext.getCurrentConversation();

        if (!messageText.isEmpty()) {
            conversationRepository.replyTo(currentConversation, replyMessage);
        }
        replyText.setText("");

        try {
            eventRepository.raiseEvent(Event.SMSReplied, new RepliedSms());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        replyButtonOnClick(view);
    }

    private class ReplyButtonEventHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            replyButtonOnClick(view);
        }
    }

    private class ReplyTextEventHandler implements TextWatcher {

        public ReplyTextEventHandler() {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (replyText.getText().toString().isEmpty()) {
                replyButton.setEnabled(false);
            } else {
                replyButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

