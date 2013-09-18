package com.skk.texting;

import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import com.google.inject.Inject;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.di.RoboSmallApplication;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.listener.ListItemClickListener;

public class ApplicationStart extends RoboSmallApplication {

    @Inject PersonFactory personFactory;
    @Inject ListItemClickListener itemClickListener;
    ListView messagesListView;

    @Override
    protected void onCreate() {
        super.onCreate();
        setContentView(R.layout.app_start);

        messagesListView = (ListView) findViewById(R.id.messages);
        String selection = TextMessageConstants.TYPE + " != " + TextMessageConstants.MessageType.DRAFT;
        Cursor smsContent = getContentResolver().query(Uri.parse("content://sms"), null, selection, null, null);
        TextMessageAdaptor textMessageAdaptor = new TextMessageAdaptor(this, smsContent, personFactory);
        new TextMessagesView(messagesListView, textMessageAdaptor).setItemClickListener(itemClickListener);
    }
}
