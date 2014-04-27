package com.skk.texting;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.skk.texting.domain.TextMessage;
import com.skk.texting.factory.PersonFactory;

public class ConversationAdaptor extends CursorAdapter {

    private PersonFactory personFactory;

    public ConversationAdaptor(Context context, Cursor c, PersonFactory personFactory) {
        super(context, c, false);
        this.personFactory = personFactory;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        TextMessage message = TextMessage.fromCursor(cursor, personFactory);

        ViewHolder viewHolder = new ViewHolder();
        View inflated;

        if(message.IsSent()){
            inflated = layoutInflater.inflate(R.layout.conversation_me, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.sent_text);
        } else{
            inflated = layoutInflater.inflate(R.layout.conversation_them, null);
            viewHolder.textView = (TextView) inflated.findViewById(R.id.recv_text);
        }

        inflated.setTag(viewHolder);

        return inflated;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(view == null) return;

        TextMessage message = TextMessage.fromCursor(cursor, personFactory);
        ViewHolder tag = (ViewHolder) view.getTag();

        tag.textView.setText(message.getMessageText());
    }
}
