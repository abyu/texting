package com.skk.texting.adaptor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.skk.texting.async.AsyncCursorUpdate;
import com.skk.texting.async.BackgroundTask;
import com.skk.texting.R;
import com.skk.texting.constants.ApplicationConstants;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.domain.TextMessage;
import com.skk.texting.evented.Event;
import com.skk.texting.evented.EventHandler;
import com.skk.texting.evented.EventRepository;
import com.skk.texting.evented.HandleEvent;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.eventdata.IncomingSmsData;

public class TextMessageAdaptor extends CursorAdapter implements EventHandler, BackgroundTask {

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
        return layoutInflater.inflate(R.layout.msg_row, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(view == null) return;
        TextView contactName = (TextView)view.findViewById(R.id.contact_name);

        TextMessage textMessage = TextMessage.fromCursor(cursor, personFactory);
        contactName.setText(textMessage.getDisplayName());

        Drawable drawable = view.getResources().getDrawable(R.drawable.touch_text_view);
        if(textMessage.isUnread())
            drawable = view.getResources().getDrawable(R.drawable.unread_text_view);

        view.setBackground(drawable);

    }

    @HandleEvent(eventType = Event.SMSReceived)
    public boolean handleSmsReceived(IncomingSmsData eventData) {
        String originatingAddress = eventData.getSmsMessage().getOriginatingAddress();

        if(!conversationExists(originatingAddress)) {
            new AsyncCursorUpdate(this).execute();
            return true;
        }
        taskComplete(); //force reload the cursor, so that the order get reflected.
        return false;
    }

    private boolean conversationExists(String originatingAddress) {
        Cursor query = contentResolver.query(Uri.parse("content://mms-sms/conversations"), new String[]{TextMessageConstants.ADDRESS}, null, null, null);
        while (query.moveToNext()){
            String string = query.getString(query.getColumnIndex(TextMessageConstants.ADDRESS));
            if(PhoneNumberUtils.compare(string, originatingAddress)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void taskComplete() {
        Cursor smsContent = contentResolver.query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");

        changeCursor(smsContent);
    }

    @Override
    public void task() {
        int initialCount = getCursor().getCount();
        int retryCount;
        Cursor newCursor;
        try {
            do {
                Thread.sleep(5);
                newCursor = contentResolver.query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
                retryCount =+ 1;
                if(retryCount > ApplicationConstants.MAX_RETRY_COUNT) break;
            } while (!(newCursor.getCount() > initialCount));
        } catch (InterruptedException e) {
            Log.e("TEXTING:", "Exception occurred while trying to reload conversation: ", e);
        }
    }
}

