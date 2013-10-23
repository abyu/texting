package com.skk.texting;

import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import com.google.inject.Inject;
import com.skk.texting.di.RoboSmallApplication;
import com.skk.texting.domain.Conversation;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.listener.ListItemClickListener;

public class ApplicationStart extends RoboSmallApplication {

    @Inject PersonFactory personFactory;
    @Inject ListItemClickListener itemClickListener;
    @Inject Conversation conversation;
    ListView messagesListView;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.app_start);

        messagesListView = (ListView) findViewById(R.id.messages);

        Cursor smsContent = getContentResolver().query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
        TextMessageAdaptor textMessageAdaptor = new TextMessageAdaptor(this, smsContent, personFactory);
        textMessageAdaptor.setConversation(conversation);
        new TextMessagesView(messagesListView, textMessageAdaptor).setItemClickListener(itemClickListener);
    }
}
