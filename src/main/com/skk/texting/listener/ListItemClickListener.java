package com.skk.texting.listener;

import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import com.google.inject.Inject;
import com.skk.ptexting.R;
import com.skk.texting.*;
import com.skk.texting.adaptor.ConversationAdaptor;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.evented.EventRepository;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private ViewFlipper viewFlipper;
    private ConversationRepository conversationRepository;
    private EventRepository eventRepository;

    @Inject
    public ListItemClickListener(ConversationRepository conversationRepository, EventRepository eventRepository) {
        this.conversationRepository = conversationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
        String threadId = cursor.getString(cursor.getColumnIndex(TextMessageConstants.THREAD_ID));

        viewFlipper.showNext();
        View currentView = viewFlipper.getCurrentView();
        ListView listView = (ListView) currentView.findViewById(R.id.listView);

        Conversation conversation = conversationRepository.loadConversations(threadId);
        ConversationAdaptor conversationAdaptor = new ConversationAdaptor(currentView.getContext(), conversation, conversationRepository, eventRepository);
        listView.setAdapter(conversationAdaptor);

        TextingApplication applicationContext = (TextingApplication) currentView.getContext().getApplicationContext();
        applicationContext.setCurrentConversation(conversation);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;
    }
}
