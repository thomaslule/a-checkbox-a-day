package fr.lule.acad.stream;

import java.util.ArrayList;
import java.util.List;

import fr.lule.acad.event.IEvent;

public class MemoryEventStream implements IEventStream {
	
	private List<IEvent> history = new ArrayList<IEvent>();

	@Override
	public void add(IEvent event) {
		history.add(event);
	}
	
	public List<IEvent> getHistory() {
		return new ArrayList<IEvent>(history);
	}

}
