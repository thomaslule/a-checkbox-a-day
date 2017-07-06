package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventPublisher;

public class Task {

	private DecisionProjection projection;

	public static UUID add(IEventPublisher publisher, String todo, String month) {
		UUID id = UUID.randomUUID();
		publisher.publish(new TaskAdded(id, todo, month));
		return id;
	}

	public Task(List<ITaskEvent> history) {
		projection = new DecisionProjection(history);
	}

	public boolean complete(IEventPublisher publisher) {
		if (!projection.exists || projection.done) {
			return false;
		}
		TaskCompleted event = new TaskCompleted(projection.id);
		projection.apply(event);
		publisher.publish(event);
		return true;
	}

	private class DecisionProjection {

		private boolean exists = false;
		private UUID id;
		private boolean done = false;

		public DecisionProjection(List<ITaskEvent> history) {
			history.forEach(this::apply);
		}

		public void apply(ITaskEvent event) {
			if (event instanceof TaskAdded) {
				exists = true;
				id = ((TaskAdded) event).getAggregateId();
				done = false;
			} else if (event instanceof TaskCompleted) {
				done = true;
			}
		}

	}

}
