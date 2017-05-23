package fr.lule.acad.stream;

import fr.lule.acad.event.IEvent;

public interface IEventPublisher {
	
	public void publish(IEvent event);

}
