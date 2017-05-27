package fr.lule.acad.stream;

import fr.lule.acad.event.ITaskEvent;

public interface IEventPublisher {
	
	public void publish(ITaskEvent event);

}
