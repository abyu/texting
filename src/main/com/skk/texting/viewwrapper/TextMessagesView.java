package com.skk.texting.viewwrapper;

import android.widget.AdapterView;
import android.widget.ListView;
import com.skk.texting.adaptor.TextMessageAdaptor;

public class TextMessagesView {
    private ListView listView;

    public TextMessagesView(ListView listView, TextMessageAdaptor adaptor) {
        this.listView = listView;
        listView.setAdapter(adaptor);
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
       listView.setOnItemClickListener(itemClickListener);
    }

}
