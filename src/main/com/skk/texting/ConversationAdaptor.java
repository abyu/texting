package com.skk.texting;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.TextMessage;

public class ConversationAdaptor extends CursorAdapter implements BackgroundTask, EventHandler {

    private ViewProvider viewProvider;
    private ConversationRepository conversationRepository;
    private Conversation conversation;

    private volatile TextMessage replyMessage;

    public ConversationAdaptor(Context context, Conversation conversation, ViewProvider viewProvider, ConversationRepository conversationRepository, EventRepository eventRepository) {
        super(context, conversation.getCursorEntity(), false);
        this.conversation = conversation;
        this.viewProvider = viewProvider;
        this.conversationRepository = conversationRepository;
        eventRepository.register(this, Event.SMSReceived);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        TextMessage textMessage = conversation.getMessage(cursor);
        View view = getMessageView(context, textMessage);

        Button replyButton = (Button) viewProvider.getView("replyButton");
        final EditText replyText = (EditText) viewProvider.getView("replyText");

        final AsyncCursorUpdate replyToAction = new AsyncCursorUpdate(this);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replyMessage = new TextMessage(replyText.getText().toString());
                String messageText = replyMessage.getMessageText();
                if (!messageText.isEmpty()) {
                    conversationRepository.replyTo(conversation, replyMessage);
                    replyToAction.execute();
                }
                replyText.setText("");
            }
        });
        return view;
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
        getCursor().close();

        conversation = conversationRepository.loadConversations(conversation.getThreadId());

        changeCursor(conversation.getCursorEntity());
    }

    @Override
    public void task() {
        int initialCount = getCursor().getCount();
        Cursor newCursor;
        try {
            do {
                Thread.sleep(5);
                conversation = conversationRepository.loadConversations(conversation.getThreadId());
                newCursor = conversation.getCursorEntity();
            } while (!(newCursor.getCount() > initialCount));
        } catch (InterruptedException e) {
            Log.e("TEXTING:", "Exception occurred while trying to reload conversation: ", e);
        }
    }

    @Override
    public void handleEvent() {
        new AsyncCursorUpdate(this).execute();
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

