package com.skk.texting.viewwrapper;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.skk.texting.R;
import com.skk.texting.TextingApplication;
import com.skk.texting.adaptor.ContactsAdaptor;
import com.skk.texting.constants.TextMessageConstants;

public class HeaderWrapper implements View.OnClickListener {
    private View headerView;
    private Button addNewConversation;
    private ViewFlipper viewFlipper;

    public void initialize(View headerView){
        this.headerView = headerView;
        addNewConversation = (Button) headerView.findViewById(R.id.add_new_conversation);

        setUpHandlers();
    }

    private void setUpHandlers() {
        addNewConversation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(viewFlipper.getDisplayedChild() == 2) return;

        TextingApplication applicationContext = (TextingApplication) view.getContext().getApplicationContext();
        applicationContext.setCurrentConversation(null);
        viewFlipper.setDisplayedChild(2);

        View currentView = viewFlipper.getCurrentView();
        ListView contactsList = (ListView) currentView.findViewById(R.id.contacts);

        String selection = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = " + 1 + " and "+ ContactsContract.CommonDataKinds.Phone.IN_VISIBLE_GROUP + " = " + 1;
        ContentResolver contentResolver = currentView.getContext().getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        ContactsAdaptor contactsAdaptor = new ContactsAdaptor(currentView.getContext(), query);
        contactsList.setAdapter(contactsAdaptor);

    }

    public void setViewFlipper(ViewFlipper viewFlipper) {

        this.viewFlipper = viewFlipper;
    }
}
