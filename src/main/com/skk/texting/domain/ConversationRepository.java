package com.skk.texting.domain;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
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


    public Conversation replyTo(Conversation conversation, TextMessage replyMessage) {

        Conversation newConversation = conversation;
        if(conversation == null){
            newConversation = createNewConversation(replyMessage);

        }

        ContentValues sentSms = new ContentValues();
        sentSms.put(TextMessageConstants.ADDRESS, newConversation.getRecipientAddress());
        sentSms.put(TextMessageConstants.MESSAGE_TEXT, replyMessage.getMessageText());
        sentSms.put(TextMessageConstants.TYPE, TextMessageConstants.MessageType.SENT);
        sentSms.put(TextMessageConstants.THREAD_ID, newConversation.getThreadId());

        contentResolver.insert(Uri.parse("content://sms/sent"), sentSms);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(newConversation.getRecipientAddress(), null, replyMessage.getMessageText(), null, null);

        return newConversation;
    }

    private Conversation createNewConversation(TextMessage replyMessage) {
        Conversation conversation = new Conversation();
//        conversation.setRecipient(replyMessage.getPerson());
        Person person = new Person();
        person.setAddress("123");
        conversation.setRecipient(person);
        String projection = "MAX("+ TextMessageConstants.THREAD_ID +") as max_threadid";
        Cursor query = contentResolver.query(Uri.parse("content://mms-sms/conversations"), new String[]{projection}, null, null, "date DESC");
        if(query.moveToNext()) {
            conversation.setThreadId(query.getString(query.getColumnIndex("max_threadid")));
        }

        return conversation;
    }
}
