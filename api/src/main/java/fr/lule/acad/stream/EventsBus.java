package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.IEvent;

public class EventsBus implements IEventPublisher {
	
	private IEventStream stream;
	private List<IEventSubscriber> subscribers = new ArrayList<IEventSubscriber>();

	public EventsBus(IEventStream stream) {
		this.stream = stream;
	}

	public void publish(IEvent event) {
		stream.add(event);
		subscribers.forEach(subscriber -> {
			subscriber.handle(event);
		});
	}

	public void subscribe(IEventSubscriber subscriber) {
		subscribers.add(subscriber);
	}

}
