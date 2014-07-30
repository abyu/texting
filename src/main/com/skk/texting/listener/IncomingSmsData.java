package com.skk.texting.listener;

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
