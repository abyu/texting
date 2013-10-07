package com.skk.texting.factory;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.google.inject.Inject;
import com.skk.texting.domain.Person;

public class PersonFactory {
    private ContentResolver contentResolver;

    @Inject
    public PersonFactory(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Person fromAddress(String address) {
        Person person = new Person();
        person.setAddress(address);
        if (address != null && !address.isEmpty()) {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
            Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor.moveToFirst()) {
                person.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)));
            }
        }
        return person;
    }
}
