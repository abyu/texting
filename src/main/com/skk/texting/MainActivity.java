package com.skk.texting;

import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.listener.ListItemClickListener;
import com.sony.smallapp.SmallApplication;

public class MainActivity extends SmallApplication {

    @Override
    protected void onCreate() {
        super.onCreate();

        setContentView(R.layout.app_mian);

        ListView listView = (ListView)findViewById(R.id.messages);
        Cursor smsContent = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        TextMessageAdaptor textMessageAdaptor = new TextMessageAdaptor(this, smsContent, new PersonFactory(getContentResolver()));
        new TextMessagesView(listView, textMessageAdaptor).setItemClickListener(new ListItemClickListener(this));

    }
}
