package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.store.IEventStore;

public class EventsBus implements IEventPublisher {
	
	private IEventStore<IItemEvent> itemEventStore;
	private List<IEventSubscriber> subscribers = new ArrayList<IEventSubscriber>();

	public EventsBus(IEventStore<IItemEvent> itemEventStore) {
		this.itemEventStore = itemEventStore;
	}

	public void publish(IItemEvent event) {
		itemEventStore.add(event);
		subscribers.forEach(subscriber -> {
			subscriber.handle(event);
		});
	}

	public void subscribe(IEventSubscriber subscriber) {
		subscribers.add(subscriber);
	}

}
