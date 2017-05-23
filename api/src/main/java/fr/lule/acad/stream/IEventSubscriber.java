package fr.lule.acad.stream;

import fr.lule.acad.event.IEvent;

public interface IEventSubscriber {
	
	public void handle(IEvent event);
	
}
