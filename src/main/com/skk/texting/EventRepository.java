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

        ArrayList<WeakReference<EventHandler>> eventHandlers = events.get(eventName);
        if(eventHandlers == null)
            eventHandlers = new ArrayList<WeakReference<EventHandler>>();

        eventHandlers.add(new WeakReference<EventHandler>(handler));
        events.put(eventName, eventHandlers);
    }

    public ArrayList<EventHandler> getHandlers(Event eventName){
        ArrayList<WeakReference<EventHandler>> weakHandlers = events.get(eventName);
        ArrayList<EventHandler> handlers = new ArrayList<EventHandler>();

        for(WeakReference<EventHandler> weakHandler : weakHandlers){
            EventHandler handler = weakHandler.get();
            if(handler != null){
                handlers.add(handler);
            }
        }
        return handlers;
    }


}


