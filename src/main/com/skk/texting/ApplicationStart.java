package com.skk.texting;

import android.database.Cursor;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.skk.texting.wrapper.MessageConsoleWrapper;


public class ApplicationStart extends RoboSmallApplication {

    @Inject PersonFactory personFactory;
    @Inject ListItemClickListener itemClickListener;
    @Inject EventRepository eventRepository;

    ListView messagesListView;
    @Inject MessageConsoleWrapper messageConsoleWrapper;

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

        viewFlipper.addView(messageConsole);

        messageConsoleWrapper.initialize(messageConsole);
        return  viewFlipper;
    }


}

