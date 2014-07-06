package com.skk.texting.listener;

import android.content.ContentResolver;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import com.google.inject.Inject;
import com.skk.texting.ConversationAdaptor;
import com.skk.texting.R;
import com.skk.texting.ViewProvider;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.domain.Conversation;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.factory.PersonFactory;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private ViewFlipper viewFlipper;
    private ConversationRepository conversationRepository;
    private PersonFactory personFactory;
    private ViewProvider viewProvider;
    private ContentResolver contentResolver;

    @Inject
    public ListItemClickListener(ConversationRepository conversationRepository, PersonFactory personFactory, ViewProvider viewProvider, ContentResolver contentResolver) {
        this.conversationRepository = conversationRepository;
        this.personFactory = personFactory;
        this.viewProvider = viewProvider;
        this.contentResolver = contentResolver;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
        String threadId = cursor.getString(cursor.getColumnIndex(TextMessageConstants.THREAD_ID));

        viewFlipper.showNext();
        View currentView = viewFlipper.getCurrentView();
        ListView listView = (ListView) currentView.findViewById(R.id.listView);

        Conversation conversation = conversationRepository.loadConversations(threadId);
        ConversationAdaptor conversationAdaptor = new ConversationAdaptor(currentView.getContext(), conversation, viewProvider, conversationRepository);
        listView.setAdapter(conversationAdaptor);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;
    }
}
