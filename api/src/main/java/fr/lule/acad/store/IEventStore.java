package fr.lule.acad.store;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IEvent;

public interface IEventStore {

	void add(UUID aggregateId, IEvent event);

	List<IEvent> getAllEvents();

	List<IEvent> getEventsFor(UUID aggregateId);

}
