package com.skk.texting;

import android.view.MotionEvent;

import java.util.HashMap;

public class MotionEventRecorder {

    private static HashMap<Integer,MotionEvent> recordedEvents = new HashMap<Integer, MotionEvent>();

    public static void recordEvent(MotionEvent event, int id){

        MotionEvent newEvent = MotionEvent.obtain(event);
        recordedEvents.put(id, newEvent);
    }

    public static MotionEvent replayEvent(int id){
        MotionEvent remove = recordedEvents.remove(id);
        return remove;
    }
}
