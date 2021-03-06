package com.skk.texting.customview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.skk.texting.constants.ApplicationConstants;
import com.skk.texting.gesture.MotionEventRecorder;

public class CustomRelativeLayout extends RelativeLayout {

    private MotionEvent previousEvent;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomRelativeLayout(){
      super(null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            MotionEventRecorder.recordEvent(ev, ApplicationConstants.RECORDED_START_SWIPE);
            previousEvent = MotionEventRecorder.peekEvent(ApplicationConstants.RECORDED_START_SWIPE);
        }

        return isAPossibleFling(previousEvent, ev) || super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setLayoutTransition(LayoutTransition transition) {
        transition.setDuration(200);
        super.setLayoutTransition(transition);
    }

    private boolean isAPossibleFling(MotionEvent previousEvent, MotionEvent ev) {
        if (previousEvent == null) return false;
        float displacementX = ev.getX() - previousEvent.getX();
        float displacementY = ev.getY() - previousEvent.getY();

        return Math.abs(displacementX) >= 100 || Math.abs(displacementY) >= 100;
    }
}
