package fr.lule.acad.store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.lule.acad.event.IEvent;

public class InMemoryEventStore<TAggregateEvent extends IEvent<?>, TID> implements IEventStore<TAggregateEvent, TID> {

	private List<TAggregateEvent> events = new ArrayList<TAggregateEvent>();

	@Override
	public void add(TAggregateEvent event) {
		events.add(event);
	}

	@Override
	public List<TAggregateEvent> getAllEvents() {
		return new ArrayList<TAggregateEvent>(events);
	}

	@Override
	public List<TAggregateEvent> getEventsFor(TID aggregateId) {
		return events.stream().filter(event -> event.getAggregateId().equals(aggregateId)).collect(Collectors.toList());
	}

}
