package com.skk.texting.listener;

import android.test.mock.MockContext;
import org.junit.Test;

public class ListItemClickListenerTest {

    @Test
    public void dependenciesTest(){
       new ListItemClickListener(new MockContext());
    }
}
