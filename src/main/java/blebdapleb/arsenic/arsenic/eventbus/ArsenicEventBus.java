package blebdapleb.arsenic.arsenic.eventbus;

import blebdapleb.arsenic.arsenic.eventbus.handler.EventHandler;
import org.apache.logging.log4j.Logger;
import blebdapleb.arsenic.arsenic.event.Event;

import java.util.concurrent.atomic.AtomicLong;

public class ArsenicEventBus {

	private final EventHandler handler;
	private final AtomicLong eventsPosted = new AtomicLong();

	private final Logger logger;

	public ArsenicEventBus(EventHandler handler, Logger logger) {
		this.handler = handler;
		this.logger = logger;
	}

	public boolean subscribe(Object object) {
		return handler.subscribe(object);
	}

	public boolean unsubscribe(Object object) {
		return handler.unsubscribe(object);
	}

	public void post(Event event) {
		handler.post(event, logger);
		eventsPosted.getAndIncrement();
	}

	public long getEventsPosted() {
		return eventsPosted.get();
	}
}
