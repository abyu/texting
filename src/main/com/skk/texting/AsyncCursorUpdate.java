package com.skk.texting;

import android.os.AsyncTask;

public class AsyncCursorUpdate extends AsyncTask<Void, Void, Void> {

    private BackgroundTask backgroundTask;

    public AsyncCursorUpdate(BackgroundTask backgroundTask){

        this.backgroundTask = backgroundTask;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        backgroundTask.task();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        backgroundTask.taskComplete();
    }
}
