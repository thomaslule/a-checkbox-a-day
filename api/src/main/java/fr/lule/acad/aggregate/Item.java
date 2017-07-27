package fr.lule.acad.aggregate;

import java.util.List;
import java.util.UUID;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemMoved;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;

public class Item {

	private DecisionProjection projection;

	public static UUID add(EventBus publisher, String text, String month, ItemType type) {
		UUID id = UUID.randomUUID();
		publisher.post(new ItemAdded(id, text, month, type));
		return id;
	}

	public Item(List<IItemEvent> history) {
		projection = new DecisionProjection(history);
	}

	public boolean cancel(EventBus publisher) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		ItemCancelled event = new ItemCancelled(projection.id);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean restore(EventBus publisher) {
		if (!projection.exists || !projection.cancelled) {
			return false;
		}
		ItemRestored event = new ItemRestored(projection.id);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean delete(EventBus publisher) {
		if (!projection.exists || !projection.cancelled) {
			return false;
		}
		ItemDeleted event = new ItemDeleted(projection.id);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean completeTask(EventBus publisher) {
		if (!projection.exists || projection.cancelled || projection.type != ItemType.TASK || projection.done) {
			return false;
		}
		TaskCompleted event = new TaskCompleted(projection.id);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean uncompleteTask(EventBus publisher) {
		if (!projection.exists || projection.cancelled || projection.type != ItemType.TASK || !projection.done) {
			return false;
		}
		TaskUncompleted event = new TaskUncompleted(projection.id);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean changeItemText(EventBus publisher, String newText) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		ItemTextChanged event = new ItemTextChanged(projection.id, newText);
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean moveItem(EventBus publisher, String moveToMonth) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		UUID newId = add(publisher, projection.text, moveToMonth, projection.type);
		ItemMoved event = new ItemMoved(projection.id, newId, moveToMonth);
		applyAndPublish(publisher, event);
		return true;
	}

	public void applyAndPublish(EventBus publisher, IItemEvent event) {
		projection.apply(event);
		publisher.post(event);
	}

	private class DecisionProjection {

		private boolean exists = false;
		private UUID id;
		private ItemType type;
		private boolean done = false;
		private boolean cancelled = false;
		private String text;

		public DecisionProjection(List<IItemEvent> history) {
			history.forEach(this::apply);
		}

		public void apply(IItemEvent event) {
			if (event instanceof ItemAdded) {
				exists = true;
				id = ((ItemAdded) event).getAggregateId();
				type = ((ItemAdded) event).getType();
				done = false;
				text = ((ItemAdded) event).getText();
			} else if (event instanceof ItemCancelled) {
				cancelled = true;
			} else if (event instanceof ItemRestored) {
				cancelled = false;
			} else if (event instanceof ItemDeleted) {
				exists = false;
			} else if (event instanceof TaskCompleted) {
				done = true;
			} else if (event instanceof TaskUncompleted) {
				done = false;
			} else if (event instanceof ItemTextChanged) {
				text = ((ItemTextChanged) event).getNewText();
			}
		}

	}

}
