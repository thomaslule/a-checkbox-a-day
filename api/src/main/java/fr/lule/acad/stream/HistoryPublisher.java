package fr.lule.acad.stream;

import java.util.List;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.store.InMemoryEventStore;

public class HistoryPublisher extends EventsBus {

	public HistoryPublisher() {
		// throw-away event store
		super(new InMemoryEventStore<IItemEvent>());
	}
	
	public void publishAll(List<? extends IEvent> history) {
		history.forEach(event -> publish(event));
	}

}
