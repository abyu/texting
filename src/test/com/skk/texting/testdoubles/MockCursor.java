package com.skk.texting.testdoubles;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

public class MockCursor implements Cursor {
    @Override
    public int getCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getPosition() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean move(int i) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean moveToPosition(int i) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean moveToFirst() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean moveToLast() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean moveToNext() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean moveToPrevious() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isFirst() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLast() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isBeforeFirst() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAfterLast() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getColumnIndex(String s) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getColumnName(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] getColumnNames() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getColumnCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public byte[] getBlob(int i) {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getString(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public short getShort(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getInt(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getLong(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public float getFloat(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public double getDouble(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getType(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNull(int i) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deactivate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean requery() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isClosed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bundle getExtras() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bundle respond(Bundle bundle) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
