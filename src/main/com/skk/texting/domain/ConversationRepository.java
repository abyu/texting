package com.skk.texting.domain;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.google.inject.Inject;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.factory.PersonFactory;

import java.util.ArrayList;

public class ConversationRepository {

    private ContentResolver contentResolver;
    private PersonFactory personFactory;

    @Inject
    public ConversationRepository(ContentResolver contentResolver, PersonFactory personFactory) {
        this.contentResolver = contentResolver;
        this.personFactory = personFactory;
    }

    public Conversation loadConversations(String threadId){
        ArrayList<TextMessage> messages;
        String selection = TextMessageConstants.THREAD_ID + " = '" + threadId +"'";
        Cursor result = contentResolver.query(Uri.parse("content://sms"), null, selection, null, null);

        messages = new ArrayList<TextMessage>();
        while(result.moveToNext()){
            messages.add(TextMessage.fromCursor(result, personFactory));
        }

       return new Conversation(messages);
    }

    public Cursor getCusor(String threadId){
        String selection = TextMessageConstants.THREAD_ID + " = '" + threadId +"'";
        Cursor result = contentResolver.query(Uri.parse("content://sms"), null, selection, null, null);

        return result;
    }

}
