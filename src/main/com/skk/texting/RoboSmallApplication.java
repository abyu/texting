package com.skk.texting;

import com.google.inject.Key;
import com.sony.smallapp.SmallApplication;
import roboguice.RoboGuice;
import roboguice.context.event.OnCreateEvent;
import roboguice.context.event.OnDestroyEvent;
import roboguice.context.event.OnStartEvent;
import roboguice.event.EventManager;
import roboguice.inject.RoboInjector;
import roboguice.util.RoboContext;

import java.util.HashMap;
import java.util.Map;

public class RoboSmallApplication extends SmallApplication implements RoboContext {
    protected EventManager eventManager;
    protected HashMap<Key<?>,Object> scopedObjects = new HashMap<Key<?>, Object>();

    @Override
    protected void onCreate() {
        final RoboInjector injector = RoboGuice.getInjector(this);
        eventManager = injector.getInstance(EventManager.class);
        injector.injectMembers(this);
        super.onCreate();
        eventManager.fire(new OnCreateEvent<SmallApplication>(this, null));
    }


    @Override
    protected void onStart() {
        super.onStart();
        eventManager.fire(new OnStartEvent<SmallApplication>(this));
    }


    //TODO: Need to override onStop()

    @Override
    protected void onDestroy() {
        try {
            eventManager.fire(new OnDestroyEvent<SmallApplication>(this));
        } finally {
            try {
                RoboGuice.destroyInjector(this);
            } finally {
                super.onDestroy();
            }
        }
    }


    @Override
    public Map<Key<?>, Object> getScopedObjectMap() {
        return scopedObjects;
    }

}
