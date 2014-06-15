package com.skk.texting;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeGestureHandler implements SwipeGestureHandler, View.OnTouchListener {

    private final GestureDetector gestureDetector;


    public OnSwipeGestureHandler(Context context) {
        gestureDetector = new GestureDetector(context, new SwipeGestureListener(this));
    }

    @Override
    public boolean onSwipeLeft() {

        return false;
    }

    @Override
    public boolean onSwipeRight() {

        return false;
    }

    @Override
    public boolean onSwipeUp() {

        return false;
    }

    @Override
    public boolean onSwipeDown() {

        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
