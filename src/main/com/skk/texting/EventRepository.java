package com.skk.texting;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;

@Singleton
public final class EventRepository {

    private final HashMap<Event, ArrayList<EventHandler>> events;
    private static EventRepository instance = new EventRepository();

    protected EventRepository(){
        events = new HashMap<Event, ArrayList<EventHandler>>();
    }

    public static EventRepository getInstance(){
        return instance;
    }

    public void register(EventHandler handler, Event eventName){

        ArrayList<EventHandler> eventHandlers = events.get(eventName);
        if(eventHandlers == null)
            eventHandlers = new ArrayList<EventHandler>();

        eventHandlers.add(handler);
        events.put(eventName, eventHandlers);
    }

    public ArrayList<EventHandler> getHandlers(Event eventName){
        return events.get(eventName);
    }


}


