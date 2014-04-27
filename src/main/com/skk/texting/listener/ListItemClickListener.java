package com.skk.texting.listener;

import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import com.google.inject.Inject;
import com.skk.texting.ConversationAdaptor;
import com.skk.texting.R;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.domain.ConversationRepository;
import com.skk.texting.factory.PersonFactory;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private ViewFlipper viewFlipper;
    private ConversationRepository conversationRepository;
    private PersonFactory personFactory;

    @Inject
    public ListItemClickListener(ConversationRepository conversationRepository, PersonFactory personFactory) {
        this.conversationRepository = conversationRepository;
        this.personFactory = personFactory;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
        String threadId = cursor.getString(cursor.getColumnIndex(TextMessageConstants.THREAD_ID));

        viewFlipper.showNext();
        View currentView = viewFlipper.getCurrentView();
        ListView listView = (ListView) currentView.findViewById(R.id.listView);

        ConversationAdaptor conversationAdaptor = new ConversationAdaptor(currentView.getContext(), conversationRepository.getCusor(threadId), personFactory);
        listView.setAdapter(conversationAdaptor);
    }

    public void setViewFlipper(ViewFlipper viewFlipper) {
        this.viewFlipper = viewFlipper;
    }
}
