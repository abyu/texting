package com.skk.texting.adaptor;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.skk.texting.R;
import com.skk.texting.domain.Person;

public class ContactsAdaptor extends CursorAdapter {
    public ContactsAdaptor(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
       LayoutInflater inflater = LayoutInflater.from(context);

       return inflater.inflate(R.layout.contact, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Person person = Person.fromCursor(cursor);

        TextView contactName = (TextView) view.findViewById(R.id.contact_name);
        TextView address = (TextView) view.findViewById(R.id.contact_address);
        TextView contactType = (TextView) view.findViewById(R.id.contact_type);

        contactName.setText(person.getName());
        address.setText(person.getAddress());
        contactType.setText(person.getContactType());
    }
}
