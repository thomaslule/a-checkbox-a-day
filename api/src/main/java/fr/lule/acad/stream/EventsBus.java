package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.store.IEventStore;

public class EventsBus implements IEventPublisher {
	
	private IEventStore store;
	private List<IEventSubscriber> subscribers = new ArrayList<IEventSubscriber>();

	public EventsBus(IEventStore store) {
		this.store = store;
	}

	public void publish(IEvent event) {
		store.add(event);
		subscribers.forEach(subscriber -> {
			subscriber.handle(event);
		});
	}

	public void subscribe(IEventSubscriber subscriber) {
		subscribers.add(subscriber);
	}

}
