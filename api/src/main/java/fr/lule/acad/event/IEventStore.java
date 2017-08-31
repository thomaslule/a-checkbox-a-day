package fr.lule.acad.event;

import java.util.List;

public interface IEventStore<TAggregateEvent extends IEvent, TID> {

	void add(TAggregateEvent event);

	List<TAggregateEvent> getAllEvents();

	List<TAggregateEvent> getEventsFor(TID aggregateId);

}
