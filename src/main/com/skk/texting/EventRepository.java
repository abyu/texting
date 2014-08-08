package com.skk.texting;

import com.google.inject.Singleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public final class EventRepository {

    private final HashMap<Event, ArrayList<WeakReference<EventHandler>>> events;
    private static EventRepository instance = new EventRepository();

    protected EventRepository(){
        events = new HashMap<Event, ArrayList<WeakReference<EventHandler>>>();
    }

    public static EventRepository getInstance(){
        return instance;
    }

    public void register(EventHandler handler, Event eventName){

        ArrayList<WeakReference<EventHandler>> eventHandlers = emptyIfNull(events.get(eventName));

        if(!getInstances(eventHandlers).contains(handler))
            eventHandlers.add(new WeakReference<EventHandler>(handler));

        events.put(eventName, eventHandlers);
    }

    public ArrayList<EventHandler> getHandlers(Event event){
        ArrayList<WeakReference<EventHandler>> weakHandlers = events.get(event);

        return getInstances(weakHandlers);
    }

    private <T> ArrayList<T> emptyIfNull(ArrayList<T> list){
        return list != null ? list : new ArrayList<T>();
    }

    private <T> ArrayList<T> getInstances(ArrayList<WeakReference<T>> weakReferences){
        ArrayList<T> instances = new ArrayList<T>();

        for (WeakReference<T> weakReference : emptyIfNull(weakReferences)) {
            T instance = weakReference.get();
            if (instance != null) {
                instances.add(instance);
            }
        }

        return instances;
    }
}


