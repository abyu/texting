package com.skk.texting.adaptor;

import android.content.Context;
import android.database.Cursor;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.skk.texting.*;
import com.skk.texting.adaptor.view.ViewHolder;
import com.skk.texting.adaptor.view.ViewType;
import com.skk.texting.async.AsyncCursorUpdate;
import com.skk.texting.async.BackgroundTask;
import com.skk.texting.constants.ApplicationConstants;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.TextMessage;
import com.skk.texting.evented.Event;
import com.skk.texting.evented.EventHandler;
import com.skk.texting.evented.EventRepository;
import com.skk.texting.evented.HandleEvent;
import com.skk.texting.eventdata.IncomingSmsData;
import com.skk.texting.eventdata.RepliedSms;

public class ConversationAdaptor extends CursorAdapter implements BackgroundTask, EventHandler {

    private ConversationRepository conversationRepository;
    private Conversation conversation;

    public ConversationAdaptor(Context context, Conversation conversation, ConversationRepository conversationRepository, EventRepository eventRepository) {
        super(context, conversation.getCursorEntity(), false);
        this.conversation = conversation;
        this.conversationRepository = conversationRepository;
        eventRepository.register(this, Event.SMSReceived);
        eventRepository.register(this, Event.SMSReplied);
        Log.d("TEXTING:", conversation.getCursorEntity().getCount() + "- Count");
        Log.d("TEXTING:", conversation.getThreadId() + "- Thread");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        TextMessage textMessage = conversation.getMessage(cursor);

        return getMessageView(context, textMessage);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            ViewHolder tag = (ViewHolder) convertView.getTag();
            getCursor().moveToPosition(position);

            if (tag.viewType != getViewType(getCursor()))
                convertView = null;
        }

        return super.getView(position, convertView, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (view == null) return;

        TextMessage textMessage = conversation.getMessage(cursor);
        ViewHolder tag = (ViewHolder) view.getTag();

        tag.textView.setText(textMessage.getMessageText());
    }

    @Override
    public void taskComplete() {

        conversation = conversationRepository.loadConversations(conversation.getThreadId());

        changeCursor(conversation.getCursorEntity());
    }

    @Override
    public void task() {
        int initialCount = getCursor().getCount();
        Cursor newCursor;
        int retryCount = 0;
        try {
            do {
                Thread.sleep(5);
                conversation = conversationRepository.loadConversations(conversation.getThreadId());
                newCursor = conversation.getCursorEntity();
                retryCount++;
                if(retryCount > ApplicationConstants.MAX_RETRY_COUNT) break;
            } while (!(newCursor.getCount() > initialCount));
        } catch (InterruptedException e) {
            Log.e("TEXTING:", "Exception occurred while trying to reload conversation: ", e);
        }
    }

    @HandleEvent(eventType = Event.SMSReceived)
    public boolean handleSmsReceived(IncomingSmsData eventData) {
        SmsMessage smsMessage = eventData.getSmsMessage();
        if(smsMessage != null && PhoneNumberUtils.compare(smsMessage.getOriginatingAddress(), (conversation.getRecipientAddress()))) {

            AsyncCursorUpdate asyncCursorUpdate = new AsyncCursorUpdate(this);
            asyncCursorUpdate.execute();

            return true;
        }

        return false;
    }

    @HandleEvent(eventType = Event.SMSReplied)
    public boolean handleSmsReplied(RepliedSms eventData){
        AsyncCursorUpdate replyToAction = new AsyncCursorUpdate(this);
        replyToAction.execute();

        return true;
    }

    private ViewType getViewType(Cursor cursor) {
        TextMessage message = conversation.getMessage(cursor);

        return getViewType(message);
    }

    private ViewType getViewType(TextMessage message) {
        return (message.isSent()) ? ViewType.SENT : ViewType.RECEIVED;
    }

    private View getMessageView(Context context, TextMessage textMessage) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHolder viewHolder = new ViewHolder();
        View inflated;

        if (textMessage.isSent()) {
            inflated = layoutInflater.inflate(R.layout.conversation_me, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.sent_text);
        } else {
            inflated = layoutInflater.inflate(R.layout.conversation_them, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.recv_text);
        }

        viewHolder.viewType = getViewType(textMessage);
        inflated.setTag(viewHolder);
        return inflated;
    }

}

