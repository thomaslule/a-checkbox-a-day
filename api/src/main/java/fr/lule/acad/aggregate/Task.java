package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventPublisher;

public class Task {
	
	private DecisionProjection projection;
	
	public static UUID add(IEventPublisher publisher, String todo) {
		UUID id = UUID.randomUUID();
		publisher.publish(new TaskAdded(id, todo));
		return id;
	}
	
	public Task(List<IEvent> history) {
		projection = new DecisionProjection(history);
	}

	public void complete(IEventPublisher publisher) {
		if (projection.done) {
			return;
		}
		TaskCompleted event = new TaskCompleted(projection.id);
		projection.apply(event);
		publisher.publish(event);
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
