package com.skk.texting.viewwrapper;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.skk.texting.R;
import com.skk.texting.adaptor.ContactsAdaptor;

public class AllContactsWrapper {

    private View allContacts;
    private EditText contactSearch;

    public void initialize(View allContacts){

        this.allContacts = allContacts;

        contactSearch = (EditText) allContacts.findViewById(R.id.search_contact);


        setUpHandlers();
    }

    private void setUpHandlers() {
        final ListView contactsList = (ListView) allContacts.findViewById(R.id.contacts);
        contactSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ContactsAdaptor adapter = (ContactsAdaptor) contactsList.getAdapter();

                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
