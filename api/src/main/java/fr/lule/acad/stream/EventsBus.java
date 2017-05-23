package fr.lule.acad.stream;

import fr.lule.acad.event.IEvent;

public class EventsBus implements IEventPublisher {
	
	private IEventStream stream;
	//private List<EventSubscriber<IEvent>> subscribers = new ArrayList<EventSubscriber<IEvent>>();

	public EventsBus(IEventStream stream) {
		this.stream = stream;
	}

	public void publish(IEvent event) {
		stream.add(event);
		/*subscribers.forEach(subscriber -> {
			if (subscriber.getClass().equals(event.getClass())) {
				subscriber.handle(event);
			}
		});*/
	}

	public void subscribe(EventSubscriber<IEvent> subscriber) {
		//subscribers.add(subscriber);
	}

}
