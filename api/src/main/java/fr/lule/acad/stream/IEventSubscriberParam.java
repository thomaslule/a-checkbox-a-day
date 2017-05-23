package fr.lule.acad.stream;

import fr.lule.acad.event.IEvent;

public interface IEventSubscriberParam<TEvent extends IEvent> extends IEventSubscriber {
	
	public void handle(TEvent event);
	
}
