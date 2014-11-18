package com.skk.ptexting.listener;

import android.telephony.SmsMessage;

public class IncomingSmsData implements EventData{
    private SmsMessage smsMessage;

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }
}
