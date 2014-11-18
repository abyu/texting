package com.skk.ptexting;

import android.view.MotionEvent;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MotionEvent.class)
public class MotionEventRecorderTest {

    private static final int SAMPLE_ID = 0;

    @Test
    public void anEventIsRecordedWithTheGivenId(){
        MotionEvent motionEvent = PowerMockito.mock(MotionEvent.class);
        mockStatic(MotionEvent.class);
        when(MotionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEvent);

        MotionEventRecorder.recordEvent(motionEvent, SAMPLE_ID);

        Assert.assertThat(MotionEventRecorder.replayEvent(SAMPLE_ID), Is.is(motionEvent));
    }

    @Test
    public void overwriteWhenEventWithGivenIdIsPresentWhenRecordingAnEvent() {
        MotionEvent motionEvent = PowerMockito.mock(MotionEvent.class);
        mockStatic(MotionEvent.class);
        when(MotionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEvent);
        MotionEventRecorder.recordEvent(motionEvent, SAMPLE_ID);

        MotionEventRecorder.recordEvent(motionEvent, SAMPLE_ID);
        Assert.assertThat(MotionEventRecorder.replayEvent(SAMPLE_ID), Is.is(motionEvent));
    }

    @Test
    public void replayOfAnEventWillRemoveEventFromRecordedCache(){
        MotionEvent motionEvent = PowerMockito.mock(MotionEvent.class);
        mockStatic(MotionEvent.class);
        when(MotionEvent.obtain(any(MotionEvent.class))).thenReturn(motionEvent);
        MotionEventRecorder.recordEvent(motionEvent, SAMPLE_ID);

        MotionEventRecorder.replayEvent(SAMPLE_ID);

        Assert.assertNull(MotionEventRecorder.replayEvent(SAMPLE_ID));
    }

}

