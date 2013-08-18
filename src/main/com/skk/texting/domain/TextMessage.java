package com.skk.texting.domain;

import android.database.Cursor;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.factory.PersonFactory;

public class TextMessage {

    private String messageText;
    private Person person;

    public TextMessage(String messageText, Person person) {
        this.messageText = messageText;
        this.person = person;
    }

    public TextMessage() {}


    public static TextMessage fromCursor(Cursor cursor, PersonFactory personFactory) {
        TextMessage textMessage = new TextMessage();

        String message = cursor.getString(cursor.getColumnIndex(TextMessageConstants.MESSAGE_TEXT));
        textMessage.setMessageText(message);

        String address = cursor.getString(cursor.getColumnIndex(TextMessageConstants.ADDRESS));
        Person person = personFactory.fromAddress(address);
        textMessage.setPerson(person);

        return textMessage;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextMessage that = (TextMessage) o;

        if (messageText != null ? !messageText.equals(that.messageText) : that.messageText != null) return false;
        if (person != null ? !person.equals(that.person) : that.person != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageText != null ? messageText.hashCode() : 0;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        return result;
    }



    @Override
    public String toString() {
        return "TextMessage{" +
                "messageText='" + messageText + '\'' +
                ", person=" + person +
                '}';
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}

