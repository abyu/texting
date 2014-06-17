package com.skk.texting.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.skk.texting.MotionEventRecorder;

public class CustomLinearLayout extends LinearLayout {

    private MotionEvent previousEvent;

    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomLinearLayout(){
      super(null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            MotionEventRecorder.recordEvent(ev, 0);
            previousEvent = MotionEventRecorder.peekEvent(0);
        }

        return isAPossibleFling(previousEvent, ev) || super.onInterceptTouchEvent(ev);
    }

    private boolean isAPossibleFling(MotionEvent previousEvent, MotionEvent ev) {
        if (previousEvent == null) return false;
        float displacementX = ev.getX() - previousEvent.getX();
        float displacementY = ev.getY() - previousEvent.getY();

        return Math.abs(displacementX) >= 100 || Math.abs(displacementY) >= 100;
    }
}
