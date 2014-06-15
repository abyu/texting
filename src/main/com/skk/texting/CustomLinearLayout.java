package com.skk.texting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {

    private MotionParams previousEvent;
    private MotionEvent lastEvent = null;
    private int action;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            lastEvent = ev;
            previousEvent = new MotionParams(ev.getX(), ev.getY(), ev.getAction());
            MotionEventRecorder.recordEvent(ev, 0);
        }

        if(isASwipe(previousEvent, ev)){
            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean isASwipe(MotionParams previousEvent, MotionEvent ev) {
        if (previousEvent == null) return false;
        float displacementX = ev.getX() - previousEvent.getX();
        float displacementY = ev.getY() - previousEvent.getY();

        return Math.abs(displacementX) >= 100 || Math.abs(displacementY) >= 100;
    }
}

class MotionParams {
    float x;
    float y;
            int action;

    MotionParams(float x, float y, int action) {
        this.x = x;
        this.y = y;
        this.action = action;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "MotionParams{" +
                "x=" + x +
                ", y=" + y +
                ", action=" + action +
                '}';
    }
}
