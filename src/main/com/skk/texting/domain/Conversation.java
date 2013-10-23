package com.skk.texting.domain;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.google.inject.Inject;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.factory.PersonFactory;

import java.util.ArrayList;

public class Conversation {

    private TextMessage lastMessage;

    //TODO: Move them in separate class
    private ArrayList<TextMessage> messages;
    private Person receiver;
    private Person me;

    private ContentResolver contentResolver;
    private PersonFactory personFactory;

    @Inject
    public Conversation(ContentResolver contentResolver, PersonFactory personFactory) {
        this.contentResolver = contentResolver;
        this.personFactory = personFactory;
    }

    public void loadConversations(String threadId){
        String selection = TextMessageConstants.THREAD_ID + " = '" + threadId +"'";
        Cursor result = contentResolver.query(Uri.parse("content://sms"), null, selection, null, null);

        messages = new ArrayList<TextMessage>();
        while(result.moveToNext()){
            messages.add(TextMessage.fromCursor(result, personFactory));
        }

        for(TextMessage message : messages){
            Log.d("TEXTING:", message.toString());
        }
    }




}
