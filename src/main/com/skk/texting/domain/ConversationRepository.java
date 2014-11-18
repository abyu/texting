package com.skk.texting.domain;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import com.google.inject.Inject;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.factory.PersonFactory;

public class ConversationRepository {

    private ContentResolver contentResolver;
    private PersonFactory personFactory;

    @Inject
    public ConversationRepository(ContentResolver contentResolver, PersonFactory personFactory) {
        this.contentResolver = contentResolver;
        this.personFactory = personFactory;
    }

    public Conversation loadConversations(String threadId) {
        String selection = TextMessageConstants.THREAD_ID + " = '" + threadId + "'";
        Cursor result = contentResolver.query(Uri.parse("content://sms"), null, selection, null, "date ASC");
        Conversation conversation = new Conversation(result);
        conversation.setThreadId(threadId);

        if (result.moveToNext()) {
            TextMessage textMessage = TextMessage.fromCursor(result, personFactory);
            conversation.setRecipient(textMessage.getPerson());
        }

        return conversation;
    }


    public void replyTo(Conversation conversation, TextMessage replyMessage) {
        ContentValues sentSms = new ContentValues();
        sentSms.put(TextMessageConstants.ADDRESS, conversation.getRecipientAddress());
        sentSms.put(TextMessageConstants.MESSAGE_TEXT, replyMessage.getMessageText());
        sentSms.put(TextMessageConstants.TYPE, TextMessageConstants.MessageType.SENT);
        sentSms.put(TextMessageConstants.THREAD_ID, conversation.getThreadId());

        contentResolver.insert(Uri.parse("content://sms/sent"), sentSms);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(conversation.getRecipientAddress(), null, replyMessage.getMessageText(), null, null);

    }

    public Conversation newConversation(Person person) {
        Conversation conversation = null;
//        Cursor query = contentResolver.query(Uri.parse("content://mms-sms/conversations"), null, null, null, "date DESC");
//        if(query.moveToNext()) {
            String selection = TextMessageConstants.ADDRESS + " = '"+ person.getAddress() +"'";

            Cursor result = contentResolver.query(Uri.parse("content://sms"), null, selection, null, "date ASC");

            conversation = new Conversation(result);
//        }

        return conversation;
    }
}
