package com.skk.texting;
    
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.skk.texting.domain.TextMessage;
import com.skk.texting.factory.PersonFactory;

public class TextMessageAdaptor extends CursorAdapter implements EventHandler, BackgroundTask{

    private PersonFactory personFactory;
    private ContentResolver contentResolver;

    public TextMessageAdaptor(Context context, Cursor c, PersonFactory personFactory, EventRepository eventRepository, ContentResolver contentResolver) {
        super(context, c, false);
        this.personFactory = personFactory;
        this.contentResolver = contentResolver;
        eventRepository.register(this, Event.SMSReceived);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.msg_row, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(view == null) return;
        TextView contactName = (TextView)view.findViewById(R.id.contact_name);

        TextMessage textMessage = TextMessage.fromCursor(cursor, personFactory);
        contactName.setText(textMessage.getDisplayName());
    }

    @Override
    public void handleEvent() {
        new AsyncCursorUpdate(this).execute();
    }

    @Override
    public void taskComplete() {
        getCursor().close();

        Cursor smsContent = contentResolver.query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");

        changeCursor(smsContent);
    }

    @Override
    public void task() {
        int initialCount = getCursor().getCount();
        Cursor newCursor;
        try {
            do {
                Thread.sleep(5);
                newCursor = contentResolver.query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
            } while (!(newCursor.getCount() > initialCount));
        } catch (InterruptedException e) {
            Log.e("TEXTING:", "Exception occurred while trying to reload conversation: ", e);
        }
    }
}

