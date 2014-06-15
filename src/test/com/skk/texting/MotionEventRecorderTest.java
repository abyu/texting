package com.skk.texting;

import android.view.MotionEvent;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MotionEventRecorderTest {

    private static final int SAMPLE_ID = 0;

    @Test
    @Ignore("Need to fix, motionevent is not fully mockable")
    public void AnEventIsRecordedWithTheGivenId(){
        MotionEvent motionEvent = mock(MotionEvent.class);
        when(motionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEvent);

        MotionEventRecorder.recordEvent(motionEvent, SAMPLE_ID);
        Assert.assertThat(MotionEventRecorder.replayEvent(SAMPLE_ID), Is.is(motionEvent));
    }



}

