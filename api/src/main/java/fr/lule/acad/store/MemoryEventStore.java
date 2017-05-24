package fr.lule.acad.store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.lule.acad.event.IEvent;

public class MemoryEventStore implements IEventStore {
	
	private List<IEvent> events = new ArrayList<IEvent>();

	@Override
	public void add(IEvent event) {
		events.add(event);
	}

	@Override
	public List<IEvent> getAllEvents() {
		return new ArrayList<IEvent>(events);
	}

	@Override
	public List<IEvent> getEventsFor(UUID aggregateId) {
		return events.stream().filter(event -> event.getAggregateId().equals(aggregateId)).collect(Collectors.toList());
	}

}
