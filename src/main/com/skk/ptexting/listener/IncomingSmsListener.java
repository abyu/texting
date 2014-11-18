package com.skk.ptexting.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.google.inject.Inject;
import com.skk.ptexting.Event;
import com.skk.ptexting.EventHandler;
import com.skk.ptexting.EventRepository;
import roboguice.receiver.RoboBroadcastReceiver;

import java.util.ArrayList;

public class IncomingSmsListener extends RoboBroadcastReceiver {

    @Inject
    public EventRepository eventRepository;

    @Override
    protected void handleReceive(Context context, Intent intent) {

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);
        EventData incomingSmsData = constructEventData(intent);

        for (EventHandler handler : handlers) {
            handler.handleEvent(incomingSmsData);
        }
    }

    private IncomingSmsData constructEventData(Intent intent) {
        IncomingSmsData incomingSmsData = new IncomingSmsData();

        Bundle extras = intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        if(pdus != null) {
            SmsMessage me = SmsMessage.createFromPdu((byte[]) pdus[0]);
            incomingSmsData.setSmsMessage(me);
        }
        return incomingSmsData;
    }
}



