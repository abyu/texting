package com.skk.texting;

import com.skk.texting.listener.EventData;

public interface EventHandler<T extends EventData>{
    public boolean handleEvent(T eventData);
}
