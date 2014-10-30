package com.skk.texting.gesture;

import android.view.MotionEvent;

import java.util.HashMap;

public class MotionEventRecorder {

    private static HashMap<String, MotionEvent> recordedEvents = new HashMap<String, MotionEvent>();

    public static void recordEvent(MotionEvent event, String id){

        MotionEvent newEvent = MotionEvent.obtain(event);
        recordedEvents.put(id, newEvent);
    }

    public static MotionEvent replayEvent(String id){
        MotionEvent remove = recordedEvents.remove(id);
        return remove;
    }

    public static MotionEvent peekEvent(String id){
       return recordedEvents.get(id);
    }

}
