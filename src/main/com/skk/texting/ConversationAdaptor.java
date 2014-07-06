package com.skk.texting;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.TextMessage;


public class ConversationAdaptor extends CursorAdapter {

    private ViewProvider viewProvider;
    private ConversationRepository conversationRepository;
    private Context context;
    private Conversation conversation;

    public ConversationAdaptor(Context context, Conversation conversation, ViewProvider viewProvider, ConversationRepository conversationRepository) {
        super(context, conversation.getCursorEntity(), false);
        this.context = context;
        this.conversation = conversation;
        this.viewProvider = viewProvider;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        TextMessage textMessage = conversation.getMessage(cursor);
        View view  = getMessageView(context, textMessage);

        Button replyButton =  (Button) viewProvider.getView("replyButton");
        final EditText replyText  = (EditText) viewProvider.getView("replyText");

        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                conversationRepository.replyTo(conversation, new TextMessage(replyText.getText().toString()));
                replyText.setText("");
                conversation = conversationRepository.loadConversations(conversation.getThreadId());
                swapCursor(conversation.getCursorEntity());
            }
        });
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null) {
            ViewHolder tag = (ViewHolder) convertView.getTag();
            getCursor().moveToPosition(position);

            if(tag.viewType != getViewType(getCursor()))
                convertView = null;
        }

        return super.getView(position, convertView, parent);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(view == null) return;

        TextMessage textMessage = conversation.getMessage(cursor);
        ViewHolder tag = (ViewHolder) view.getTag();

        tag.textView.setText(textMessage.getMessageText());
    }

    private ViewType getViewType(Cursor cursor){
        TextMessage message = conversation.getMessage(cursor);

        return getViewType(message);
    }

    private ViewType getViewType(TextMessage message){
        return (message.isSent())? ViewType.SENT : ViewType.RECEIVED;
    }

    private View getMessageView(Context context, TextMessage textMessage) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewHolder viewHolder = new ViewHolder();
        View inflated;

        if(textMessage.isSent()){
            inflated = layoutInflater.inflate(R.layout.conversation_me, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.sent_text);
        } else{

            inflated = layoutInflater.inflate(R.layout.conversation_them, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.recv_text);
        }

        viewHolder.viewType = getViewType(textMessage);
        inflated.setTag(viewHolder);
        return inflated;
    }

}
