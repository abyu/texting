package com.skk.texting;

import com.skk.texting.listener.EventData;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.MockitoAnnotations.initMocks;

public class EventRepositoryTest {

    @Mock
    private EventHandler<EventData> eventHandler1;

    @Mock
    private EventHandler<EventData> eventHandler2;

    private EventRepository eventRepository;

    @Before
    public void setUp(){
        initMocks(this);
        eventRepository = new EventRepository();

    }

    @Test
    public void aHandlerForAnEventIsRegistered(){

        eventRepository.register(eventHandler1, Event.SMSReceived);

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);

        Assert.assertThat(handlers.size(), Is.is(1));
    }

    @Test
    public void multipleHandlersForAnEventAreRegistered(){
        eventRepository.register(eventHandler1, Event.SMSReceived);
        eventRepository.register(eventHandler2, Event.SMSReceived);

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);

        Assert.assertThat(handlers.size(), Is.is(2));
    }

    @Test
    public void onlyOneInstanceOfAHandlerIsRegisteredForAnEvent(){
        eventRepository.register(eventHandler1, Event.SMSReceived);
        eventRepository.register(eventHandler1, Event.SMSReceived);

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);

        Assert.assertThat(handlers.size(), Is.is(1));
    }

    @Test
    public void emptyHandlerListIsReturnedWhenNoneRegistered(){

        ArrayList<EventHandler> handlers = eventRepository.getHandlers(Event.SMSReceived);

        Assert.assertTrue(handlers.isEmpty());
    }
}


