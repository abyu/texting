package com.skk.texting.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.inject.Inject;
import com.skk.texting.Event;
import com.skk.texting.EventHandler;
import com.skk.texting.EventRepository;
import com.skk.texting.di.RoboSmallApplication;
import roboguice.receiver.RoboBroadcastReceiver;

import java.util.ArrayList;

public class IncomingSmsListener extends RoboBroadcastReceiver {

    @Inject
    public EventRepository eventRepository;

    @Override
    protected void handleReceive(Context context, Intent intent) {

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);

        for (EventHandler handler : handlers){
            handler.handleEvent();
        }
    }
}
