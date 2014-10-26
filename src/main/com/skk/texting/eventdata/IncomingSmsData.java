package com.skk.texting.eventdata;

import android.telephony.SmsMessage;
import com.skk.texting.evented.EventData;

public class IncomingSmsData implements EventData {
    private SmsMessage smsMessage;

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }
}

