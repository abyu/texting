package com.skk.texting.listener;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private Context applicationContext;

    public ListItemClickListener(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
        Log.d("TEXTING:", cursor.getString(cursor.getColumnIndex("body")));

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.d("TEXTING:", cursor.getColumnName(i) + ":" + cursor.getString(i) + "");
        }
    }
}
