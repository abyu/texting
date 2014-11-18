package com.skk.ptexting;

import com.skk.ptexting.listener.EventData;

public interface EventHandler<T extends EventData>{
    public boolean handleEvent(T eventData);
}
