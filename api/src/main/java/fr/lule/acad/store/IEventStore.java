package fr.lule.acad.store;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IEvent;

public interface IEventStore<TAggregateEvent extends IEvent> {

	void add(TAggregateEvent event);

	List<TAggregateEvent> getAllEvents();

	List<TAggregateEvent> getEventsFor(UUID aggregateId);

}
