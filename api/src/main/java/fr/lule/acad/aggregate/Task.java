package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;

public class Task {
	
	private DecisionProjection projection;
	
	public static UUID add(List<IEvent> history, String todo) {
		UUID id = UUID.randomUUID();
		history.add(new TaskAdded(id, todo));
		return id;
	}
	
	public Task(List<IEvent> history) {
		projection = new DecisionProjection(history);
	}

	public void complete(List<IEvent> history) {
		if (projection.done) {
			return;
		}
		TaskCompleted event = new TaskCompleted(projection.id);
		projection.apply(event);
		history.add(event);
	}
	
	private class DecisionProjection {
		
		private UUID id;
		private boolean done;
		
		public DecisionProjection(List<IEvent> history) {
			history.forEach(this::apply);
		}
		
		public void apply(IEvent event) {
			if (event instanceof TaskAdded) {
				id = ((TaskAdded) event).getId();
				done = false;
			} else if (event instanceof TaskCompleted) {
				done = true;
			}
		}
		
	}


}
