package com.skk.texting.customview;

import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.skk.texting.MotionEventRecorder;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LinearLayout.class, MotionEvent.class})
public class CustomRelativeLayoutTest {

    @Test
    public void recordAnyActionDownEventDuringATouchEvent(){
        suppress(method(LinearLayout.class, "onInterceptTouchEvent"));
        CustomRelativeLayout customRelativeLayout = Whitebox.newInstance(CustomRelativeLayout.class);
        MotionEvent motionEventDown = PowerMockito.mock(MotionEvent.class);

        mockStatic(MotionEvent.class);
        when(MotionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEventDown);
        when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(motionEventDown.getX()).thenReturn(126f);
        when(motionEventDown.getY()).thenReturn(232f);

        customRelativeLayout.onInterceptTouchEvent(motionEventDown);

        Assert.assertThat(MotionEventRecorder.peekEvent(0), Is.is(motionEventDown));
    }

    @Test
    public void doNotPropagateTouchEventsToChildWhenItIsPossibleFling(){
        suppress(method(LinearLayout.class, "onInterceptTouchEvent"));
        CustomRelativeLayout customRelativeLayout = Whitebox.newInstance(CustomRelativeLayout.class);
        MotionEvent motionEventDown = PowerMockito.mock(MotionEvent.class);
        MotionEvent motionEventMove = PowerMockito.mock(MotionEvent.class);

        mockStatic(MotionEvent.class);
        when(MotionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEventDown);
        when(motionEventDown.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(motionEventDown.getX()).thenReturn(126f);
        when(motionEventDown.getY()).thenReturn(232f);

        when(motionEventMove.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(motionEventDown.getX()).thenReturn(256f);
        when(motionEventMove.getY()).thenReturn(240f);

        customRelativeLayout.onInterceptTouchEvent(motionEventDown);

        //A return value of true means, the event has been consumed here and was not propagated down.
        Assert.assertThat(customRelativeLayout.onInterceptTouchEvent(motionEventMove), Is.is(true));
    }
}
