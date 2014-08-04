package com.skk.texting;

import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.google.inject.Inject;
import com.skk.texting.di.RoboSmallApplication;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.listener.ListItemClickListener;
import com.skk.texting.listener.OnSwipeGestureHandler;


public class ApplicationStart extends RoboSmallApplication {

    @Inject PersonFactory personFactory;
    @Inject ListItemClickListener itemClickListener;
    @Inject ViewProvider viewProvider;
    @Inject EventRepository eventRepository;

    ListView messagesListView;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.app_start);

        messagesListView = (ListView) findViewById(R.id.messages);

        Cursor smsContent = getContentResolver().query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
        TextMessageAdaptor textMessageAdaptor = new TextMessageAdaptor(this, smsContent, personFactory, eventRepository, getContentResolver());

        itemClickListener.setViewFlipper(viewFlipper());

        new TextMessagesView(messagesListView, textMessageAdaptor).setItemClickListener(itemClickListener);
    }

    private ViewFlipper viewFlipper(){
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View messageConsole = layoutInflater.inflate(R.layout.msg_console, null);

        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        final ApplicationStart applicationContext = this;
        OnSwipeGestureHandler swipeListener = new OnSwipeGestureHandler(this) {
            @Override
            public boolean onSwipeRight() {
                viewFlipper.setInAnimation(applicationContext, R.anim.slide_in_left);
                viewFlipper.setOutAnimation(applicationContext, R.anim.slide_out_right);
                viewFlipper.setDisplayedChild(0);
                viewFlipper.setInAnimation(applicationContext, R.anim.slide_in_right);
                viewFlipper.setOutAnimation(applicationContext, R.anim.slide_out_left);
                return true;
            }

        };
        messageConsole.setOnTouchListener(swipeListener);

        attachReplyButtonHandler(messageConsole);
        viewFlipper.addView(messageConsole);

        return  viewFlipper;
    }

    private void attachReplyButtonHandler(View messageConsole) {
        final Button replyButton = (Button) messageConsole.findViewById(R.id.reply);
        final EditText replyText = (EditText) messageConsole.findViewById(R.id.reply_text);
        final Animation slideInUp = AnimationUtils.loadAnimation(this, R.anim.slide_in_up);
        final Animation slideOutDown = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);

        viewProvider.assignView(ConversationAdaptor.class.getName(), "replyButton", replyButton);
        viewProvider.assignView(ConversationAdaptor.class.getName(), "replyText", replyText);

        replyText.addTextChangedListener(new TextWatcher() {
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
        });
    }

}

