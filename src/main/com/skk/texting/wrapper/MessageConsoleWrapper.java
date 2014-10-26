package com.skk.texting.wrapper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import com.google.inject.Inject;
import com.skk.texting.R;
import com.skk.texting.TextingApplication;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.TextMessage;

public class MessageConsoleWrapper implements View.OnClickListener {

    private EditText replyText;
    private ConversationRepository conversationRepository;
    private Button replyButton;

    @Inject
    public MessageConsoleWrapper(ConversationRepository conversationRepository){

        this.conversationRepository = conversationRepository;
    }

    public void initialize(View messageConsole) {
        replyButton = (Button) messageConsole.findViewById(R.id.reply);
        replyText = (EditText) messageConsole.findViewById(R.id.reply_text);

        replyButton.setOnClickListener(new ReplyButtonEventHandler());
        replyText.addTextChangedListener(new ReplyTextEventHandler(messageConsole));

    }

    public void replyButtonOnClick(View view){
        TextingApplication applicationContext = (TextingApplication) view.getContext().getApplicationContext();

        TextMessage replyMessage = new TextMessage(replyText.getText().toString());
                String messageText = replyMessage.getMessageText();
                Conversation currentConversation = applicationContext.getCurrentConversation();

                if (!messageText.isEmpty()) {
                    conversationRepository.replyTo(currentConversation, replyMessage);
                }
                replyText.setText("");
            }

    @Override
    public void onClick(View view) {
        replyButtonOnClick(view);
    }

    private class ReplyButtonEventHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            replyButtonOnClick(view);
        }
    }

    private class ReplyTextEventHandler implements TextWatcher{

        private final Animation slideInUp;
        private final Animation slideOutDown;

        public ReplyTextEventHandler(View messageConsole){
            slideInUp = AnimationUtils.loadAnimation(messageConsole.getContext(), R.anim.slide_in_up);
            slideOutDown = AnimationUtils.loadAnimation(messageConsole.getContext(), R.anim.slide_out_down);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            boolean buttonVisible = replyButton.getVisibility() == View.VISIBLE;
            if(replyText.getText().toString().isEmpty()) {
                if(buttonVisible) {
                    replyButton.startAnimation(slideOutDown);
                    replyButton.setVisibility(View.GONE);
                }
            }
            else {
                if(!buttonVisible) {
                    replyButton.startAnimation(slideInUp);
                    replyButton.setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

