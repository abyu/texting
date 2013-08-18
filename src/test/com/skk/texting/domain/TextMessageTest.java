package com.skk.texting.domain;


import android.database.Cursor;
import com.skk.texting.constants.TextMessageConstants;
import com.skk.texting.factory.PersonFactory;
import com.skk.texting.testdoubles.MockCursor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class TextMessageTest {


    private Cursor cursor;
    @Mock
    private PersonFactory personFactory;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void TextMessageIsCreatedFromTheGivenCursor() {
        final String messageText = "Hello";
        final String address = "12345546";
        Person person = new Person("myname", address);

        cursor = new MockCursor() {
            @Override
            public String getString(int columnIndex) {
                if (columnIndex == 1)
                    return messageText;
                if (columnIndex == 0)
                    return address;
                return "";
            }

            @Override
            public int getColumnIndex(String s) {
                if (s.equals(TextMessageConstants.MESSAGE_TEXT))
                    return 1;
                if (s.equals(TextMessageConstants.ADDRESS))
                    return 0;
                return -1;

            }
        };

        when(personFactory.fromAddress(address)).thenReturn(person);
        TextMessage message = TextMessage.fromCursor(cursor, personFactory);

        Assert.assertThat(message, is(new TextMessage(messageText, person)));
    }

}
