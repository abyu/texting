package com.skk.texting.viewwrapper;


import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.inject.Inject;
import com.skk.texting.R;
import com.skk.texting.TextingApplication;
import com.skk.texting.adaptor.ContactsAdaptor;
import com.skk.texting.adaptor.ConversationAdaptor;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.domain.Person;
import com.skk.texting.evented.EventRepository;

public class AllContactsWrapper implements AdapterView.OnItemClickListener {

    private View allContacts;
    private EditText contactSearch;
    private ListView contactsList;

    private ConversationRepository conversationRepository;
    private ViewFlipper viewFlipper;
    private EventRepository eventRepository;

    @Inject
    public AllContactsWrapper(ConversationRepository conversationRepository, EventRepository eventRepository) {
        this.conversationRepository = conversationRepository;
        this.eventRepository = eventRepository;
    }

    public void initialize(View allContacts){

        this.allContacts = allContacts;
        contactSearch = (EditText) allContacts.findViewById(R.id.search_contact);
        contactsList = (ListView) allContacts.findViewById(R.id.contacts);
        setUpHandlers();
    }

    private void setUpHandlers() {
        contactsList.setOnItemClickListener(this);

        final ListView contactsList = this.contactsList;
        contactSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ContactsAdaptor adapter = (ContactsAdaptor) contactsList.getAdapter();
                if(adapter != null)
                    adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

        Person person = Person.fromCursor(cursor);

        Conversation conversation = conversationRepository.newConversation(person);

        conversation.setRecipient(person);

        TextingApplication applicationContext = (TextingApplication) view.getContext().getApplicationContext();
        applicationContext.setCurrentConversation(conversation);

        viewFlipper.setDisplayedChild(1);

        View currentView = viewFlipper.getCurrentView();
        ListView listView = (ListView) currentView.findViewById(R.id.listView);

        Cursor cursorEntity = conversation.getCursorEntity();
        Log.d("TEXTING:", cursorEntity.getCount() + " Count1");
        Log.d("TEXTING:", conversation.getThreadId() + " Thread1");

        ConversationAdaptor conversationAdaptor = new ConversationAdaptor(view.getContext(), conversation, conversationRepository, eventRepository);
        listView.setAdapter(conversationAdaptor);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {

        this.viewFlipper = viewFlipper;
    }
}
