package com.skk.texting.adaptor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.TextView;
import com.skk.texting.R;
import com.skk.texting.domain.Person;

public class ContactsAdaptor extends CursorAdapter {
    public ContactsAdaptor(final Context context, Cursor c) {
        super(context, c, false);
        setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                String selection = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = " + 1 + " and " + ContactsContract.CommonDataKinds.Phone.IN_VISIBLE_GROUP + " = " + 1 + " and " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE '%" + charSequence + "%' or " + ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE '"+ charSequence + "%'";
                ContentResolver contentResolver = context.getContentResolver();

                Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

                return query;
            }
        });
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
