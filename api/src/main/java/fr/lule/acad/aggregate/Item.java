package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.stream.IEventPublisher;

public class Item {

	private DecisionProjection projection;

	public static UUID add(IEventPublisher publisher, String text, String month) {
		UUID id = UUID.randomUUID();
		publisher.publish(new ItemAdded(id, text, month));
		return id;
	}

	public Item(List<IItemEvent> history) {
		projection = new DecisionProjection(history);
	}

	public boolean completeTask(IEventPublisher publisher) {
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

		public DecisionProjection(List<IItemEvent> history) {
			history.forEach(this::apply);
		}

		public void apply(IItemEvent event) {
			if (event instanceof ItemAdded) {
				exists = true;
				id = ((ItemAdded) event).getAggregateId();
				done = false;
			} else if (event instanceof TaskCompleted) {
				done = true;
			}
		}

	}

}
