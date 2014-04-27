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

public class TextMessageAdaptor extends CursorAdapter {

    private PersonFactory personFactory;

    public TextMessageAdaptor(Context context, Cursor c, PersonFactory personFactory) {
        super(context, c, false);
        this.personFactory = personFactory;
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
}

