package com.skk.ptexting;

import android.widget.AdapterView;
import android.widget.ListView;

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
