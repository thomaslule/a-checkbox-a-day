package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;
import fr.lule.acad.stream.IEventPublisher;

public class Item {

	private DecisionProjection projection;

	public static UUID add(IEventPublisher publisher, String text, String month, ItemType type) {
		UUID id = UUID.randomUUID();
		publisher.publish(new ItemAdded(id, text, month, type));
		return id;
	}

	public Item(List<IItemEvent> history) {
		projection = new DecisionProjection(history);
	}
	
	public boolean cancel(IEventPublisher publisher) {
		if (!projection.exists || projection.deleted || projection.cancelled) {
			return false;
		}
		ItemCancelled event = new ItemCancelled(projection.id);
		publisher.publish(event);
		return true;
	}

	public boolean restore(IEventPublisher publisher) {
		if (!projection.exists || projection.deleted || !projection.cancelled) {
			return false;
		}
		ItemRestored event = new ItemRestored(projection.id);
		publisher.publish(event);
		return true;
	}

	public boolean delete(IEventPublisher publisher) {
		if (!projection.exists || projection.deleted) {
			return false;
		}
		ItemDeleted event = new ItemDeleted(projection.id);
		publisher.publish(event);
		return true;
	}

	public boolean completeTask(IEventPublisher publisher) {
		if (!projection.exists || projection.deleted || projection.type != ItemType.TASK || projection.done) {
			return false;
		}
		TaskCompleted event = new TaskCompleted(projection.id);
		projection.apply(event);
		publisher.publish(event);
		return true;
	}

	public boolean uncompleteTask(IEventPublisher publisher) {
		if (!projection.exists || projection.deleted || projection.type != ItemType.TASK || !projection.done) {
			return false;
		}
		TaskUncompleted event = new TaskUncompleted(projection.id);
		projection.apply(event);
		publisher.publish(event);
		return true;
	}

	private class DecisionProjection {

		private boolean exists = false;
		private UUID id;
		private ItemType type;
		private boolean done = false;
		private boolean cancelled = false;
		private boolean deleted = false;

		public DecisionProjection(List<IItemEvent> history) {
			history.forEach(this::apply);
		}

		public void apply(IItemEvent event) {
			if (event instanceof ItemAdded) {
				exists = true;
				id = ((ItemAdded) event).getAggregateId();
				type = ((ItemAdded) event).getType();
				done = false;
			} else if (event instanceof ItemCancelled) {
				cancelled = true;
			} else if (event instanceof ItemRestored) {
				cancelled = false;
			} else if (event instanceof ItemDeleted) {
				deleted = true;
			} else if (event instanceof TaskCompleted) {
				done = true;
			} else if (event instanceof TaskUncompleted) {
				done = false;
			}
		}

	}

}
