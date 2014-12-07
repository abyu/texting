package com.skk.ptexting;

import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.inject.Inject;
import com.skk.texting.adaptor.TextMessageAdaptor;
import com.skk.texting.di.RoboSmallApplication;
import com.skk.texting.evented.EventRepository;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.listener.ListItemClickListener;
import com.skk.texting.gesture.OnSwipeGestureHandler;
import com.skk.texting.viewwrapper.AllContactsWrapper;
import com.skk.texting.viewwrapper.HeaderWrapper;
import com.skk.texting.viewwrapper.MessageConsoleWrapper;
import com.skk.texting.viewwrapper.TextMessagesView;
import com.sony.smallapp.SmallAppWindow;


public class ApplicationStart extends RoboSmallApplication {

    @Inject PersonFactory personFactory;
    @Inject ListItemClickListener itemClickListener;
    @Inject EventRepository eventRepository;
    @Inject MessageConsoleWrapper messageConsoleWrapper;
    @Inject HeaderWrapper headerWrapper;
    @Inject AllContactsWrapper allContactsWrapper;

    ListView messagesListView;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.app_start);

        messagesListView = (ListView) findViewById(R.id.messages);

        Cursor smsContent = getContentResolver().query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
        TextMessageAdaptor textMessageAdaptor = new TextMessageAdaptor(this, smsContent, personFactory, eventRepository, getContentResolver());

        itemClickListener.setViewFlipper(viewFlipper());

        setHeader();
        setMinimumDimensions();

        new TextMessagesView(messagesListView, textMessageAdaptor).setItemClickListener(itemClickListener);
    }

    private void setMinimumDimensions() {
        SmallAppWindow.Attributes attributes = getWindow().getAttributes();

        attributes.height = getResources().getDimensionPixelOffset(R.dimen.width);
        attributes.width = getResources().getDimensionPixelOffset(R.dimen.height);

        getWindow().setAttributes(attributes);
    }

    private void setHeader() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.header, null);
        getWindow().setHeaderView(headerView);

        headerWrapper.initialize(headerView);
        headerWrapper.setViewFlipper((ViewFlipper) findViewById(R.id.viewFlipper));
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

        View allContacts = layoutInflater.inflate(R.layout.all_contacts, null);

        allContactsWrapper.initialize(allContacts);
        viewFlipper.addView(allContacts);
        allContacts.setOnTouchListener(swipeListener);

        allContactsWrapper.setViewFlipper(viewFlipper);

        return  viewFlipper;
    }


}

