package com.skk.texting;

import com.skk.texting.listener.EventData;
import com.skk.texting.listener.IncomingSmsData;
import com.skk.texting.listener.RepliedSms;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Spy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventRepositoryTest {

    @Mock
    private EventHandler eventHandler1;

    @Mock
    private EventHandler eventHandler2;

    private TestHandler testHandler = new TestHandler();

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

    @Test
    public void methodsAnnotatedWithHandleEventInHandlersAreInvokedOnRaiseAnEvent() throws IllegalAccessException {
        eventRepository.register(testHandler, Event.SMSReceived);

        IncomingSmsData data = new IncomingSmsData();
        try {
            eventRepository.raiseEvent(Event.SMSReceived, data);
            Assert.fail("Expected InvocationTargetException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            Assert.assertTrue("Expected MethodInvoked exception, but was "+targetException.toString(), targetException instanceof MethodInvoked);
            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.<EventData>is(data));
        }
    }

    @Test
    public void handlersCanRegisterToMultipleEventsCorrespondingMethodIsOnlyCalled() throws IllegalAccessException {
        eventRepository.register(testHandler, Event.SMSReceived);
        eventRepository.register(testHandler, Event.SMSReplied);

        RepliedSms data = new RepliedSms();
        try {
            eventRepository.raiseEvent(Event.SMSReplied, data);
            Assert.fail("Expected InvocationTargetException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            Assert.assertTrue("Expected MethodInvoked exception, but was "+targetException.toString(), targetException instanceof MethodInvoked);
            Assert.assertThat(((MethodInvoked) targetException).getData(), Is.<EventData>is(data));
        }
    }


    private class TestHandler implements EventHandler{

        @HandleEvent(eventType = Event.SMSReceived)
        public void handleEvent(IncomingSmsData data) throws MethodInvoked {
         //Mockito does not support spying on annotated methods(the annotations are lost on the spy object), hence an exception is used assert that the method was called.
            throw new MethodInvoked(data);
        }

        @HandleEvent(eventType = Event.SMSReplied)
        public void handleSmsReplied(RepliedSms data) throws MethodInvoked {
            throw new MethodInvoked(data);
        }
    }

    private class MethodInvoked extends Exception{

        private EventData data;

        public MethodInvoked(EventData data) {

            this.data = data;
        }

        public EventData getData() {
            return data;
        }
    }
}


