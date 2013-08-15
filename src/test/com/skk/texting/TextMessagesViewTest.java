package com.skk.texting;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TextMessagesViewTest {

    @Mock public ListView listView;
    @Mock public TextMessageAdaptor textMessageAdaptor;


    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void setsUpTheListViewProvided(){
        new TextMessagesView(listView, textMessageAdaptor);

        verify(listView).setAdapter(textMessageAdaptor);
    }

    @Test
    public void bindsTheProvidedListenerToListViewsOnClickEvent(){
        TextMessagesView textMessagesView = new TextMessagesView(listView, textMessageAdaptor);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        };
        textMessagesView.setItemClickListener(itemClickListener);

        verify(listView).setOnItemClickListener(itemClickListener);
    }


}

