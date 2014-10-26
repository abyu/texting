package com.skk.texting.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import com.google.inject.Inject;
import com.skk.texting.Event;
import com.skk.texting.EventHandler;
import com.skk.texting.EventRepository;
import roboguice.receiver.RoboBroadcastReceiver;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class IncomingSmsListener extends RoboBroadcastReceiver {

    @Inject EventRepository eventRepository;

    @Override
    protected void handleReceive(Context context, Intent intent) {

        EventData incomingSmsData = constructEventData(intent);

        try {
            eventRepository.raiseEvent(Event.SMSReceived, incomingSmsData);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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



