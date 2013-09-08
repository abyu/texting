package com.skk.texting.listener;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.google.inject.Inject;

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private Context applicationContext;

    @Inject
    public ListItemClickListener(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
        view.setBackgroundColor(R.color.holo_blue_dark);
        Log.d("TEXTING:", cursor.getString(cursor.getColumnIndex("body")));

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.d("TEXTING:", cursor.getColumnName(i) + ":" + cursor.getString(i) + "");
        }
    }
}
