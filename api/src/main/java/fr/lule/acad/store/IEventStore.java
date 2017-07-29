package fr.lule.acad.store;

import java.util.List;

import fr.lule.acad.event.IEvent;

public interface IEventStore<TAggregateEvent extends IEvent, TID> {

	void add(TAggregateEvent event);

	List<TAggregateEvent> getAllEvents();

	List<TAggregateEvent> getEventsFor(TID aggregateId);

}
