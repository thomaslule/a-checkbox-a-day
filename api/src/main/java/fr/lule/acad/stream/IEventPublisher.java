package fr.lule.acad.stream;

import fr.lule.acad.event.IItemEvent;

public interface IEventPublisher {
	
	public void publish(IItemEvent event);

}
