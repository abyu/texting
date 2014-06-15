package com.skk.texting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class CustomViewFlipper extends ViewFlipper {
    public CustomViewFlipper(Context context) {
        super(context);
    }

    public CustomViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return false;
    }
}
