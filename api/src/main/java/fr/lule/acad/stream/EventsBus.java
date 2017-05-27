package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.store.IEventStore;

public class EventsBus implements IEventPublisher {
	
	private IEventStore<ITaskEvent> taskEventStore;
	private List<IEventSubscriber> subscribers = new ArrayList<IEventSubscriber>();

	public EventsBus(IEventStore<ITaskEvent> taskEventStore) {
		this.taskEventStore = taskEventStore;
	}

	public void publish(ITaskEvent event) {
		taskEventStore.add(event);
		subscribers.forEach(subscriber -> {
			subscriber.handle(event);
		});
	}

	public void subscribe(IEventSubscriber subscriber) {
		subscribers.add(subscriber);
	}

}
