package fr.lule.acad.store;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import fr.lule.acad.event.IEvent;

public class InMemoryEventStore<TAggregateEvent extends IEvent<?>> implements IEventStore<TAggregateEvent> {

	private List<TAggregateEvent> events = new ArrayList<TAggregateEvent>();

	@Override
	@Subscribe
	public void add(TAggregateEvent event) {
		events.add(event);
	}

	@Override
	public List<TAggregateEvent> getAllEvents() {
		return new ArrayList<TAggregateEvent>(events);
	}

	@Override
	public List<TAggregateEvent> getEventsFor(UUID aggregateId) {
		return events.stream().filter(event -> event.getAggregateId().equals(aggregateId)).collect(Collectors.toList());
	}

}
