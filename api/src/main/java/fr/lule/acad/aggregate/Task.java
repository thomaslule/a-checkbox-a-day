package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.ITaskEvent;
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
	
	public Task(List<ITaskEvent> history) {
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
		
		public DecisionProjection(List<ITaskEvent> history) {
			history.forEach(this::apply);
		}
		
		public void apply(ITaskEvent event) {
			if (event instanceof TaskAdded) {
				id = ((TaskAdded) event).getAggregateId();
				done = false;
			} else if (event instanceof TaskCompleted) {
				done = true;
			}
		}
		
	}


}
