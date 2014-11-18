package com.skk.ptexting.listener;

import android.content.ContentResolver;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import com.google.inject.Inject;
import com.skk.ptexting.ConversationAdaptor;
import com.skk.ptexting.EventRepository;
import com.skk.ptexting.R;
import com.skk.ptexting.ViewProvider;
import com.skk.ptexting.constants.TextMessageConstants;
import com.skk.ptexting.domain.Conversation;
import com.skk.ptexting.domain.ConversationRepository;
import com.skk.ptexting.factory.PersonFactory;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private ViewFlipper viewFlipper;
    private ConversationRepository conversationRepository;
    private PersonFactory personFactory;
    private ViewProvider viewProvider;
    private ContentResolver contentResolver;
    private EventRepository eventRepository;

    @Inject
    public ListItemClickListener(ConversationRepository conversationRepository, PersonFactory personFactory, ViewProvider viewProvider, ContentResolver contentResolver, EventRepository eventRepository) {
        this.conversationRepository = conversationRepository;
        this.personFactory = personFactory;
        this.viewProvider = viewProvider;
        this.contentResolver = contentResolver;
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
        ConversationAdaptor conversationAdaptor = new ConversationAdaptor(currentView.getContext(), conversation, viewProvider, conversationRepository, eventRepository);
        listView.setAdapter(conversationAdaptor);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;
    }
}
