package fr.lule.acad.item;

import java.util.List;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemCancelled;
import fr.lule.acad.item.event.ItemDeleted;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.ItemMoved;
import fr.lule.acad.item.event.ItemRestored;
import fr.lule.acad.item.event.ItemTextChanged;
import fr.lule.acad.item.event.TaskCompleted;
import fr.lule.acad.item.event.TaskUncompleted;
import fr.lule.acad.util.IDateFactory;
import fr.lule.acad.util.IIdFactory;

public class Item {

	private final IDateFactory dateFactory;
	private final IIdFactory idFactory;
	private DecisionProjection projection;

	public static ItemId add(EventBus publisher, String text, String month, ItemType type, IDateFactory dateFactory,
			IIdFactory idFactory) {
		ItemAdded event = new ItemAdded(text, month, type, new ItemId(idFactory.newId()), dateFactory.now());
		publisher.post(event);
		return event.getAggregateId();
	}

	public Item(List<ItemEvent> history, IDateFactory dateFactory, IIdFactory idFactory) {
		this.dateFactory = dateFactory;
		this.idFactory = idFactory;
		projection = new DecisionProjection(history);
	}

	public boolean cancel(EventBus publisher) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		ItemCancelled event = new ItemCancelled(projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean restore(EventBus publisher) {
		if (!projection.exists || !projection.cancelled) {
			return false;
		}
		ItemRestored event = new ItemRestored(projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean delete(EventBus publisher) {
		if (!projection.exists || !projection.cancelled) {
			return false;
		}
		ItemDeleted event = new ItemDeleted(projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean completeTask(EventBus publisher) {
		if (!projection.exists || projection.cancelled || projection.type != ItemType.TASK || projection.done) {
			return false;
		}
		TaskCompleted event = new TaskCompleted(projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean uncompleteTask(EventBus publisher) {
		if (!projection.exists || projection.cancelled || projection.type != ItemType.TASK || !projection.done) {
			return false;
		}
		TaskUncompleted event = new TaskUncompleted(projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean changeItemText(EventBus publisher, String newText) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		ItemTextChanged event = new ItemTextChanged(newText, projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public boolean moveItem(EventBus publisher, String moveToMonth) {
		if (!projection.exists || projection.cancelled) {
			return false;
		}
		ItemId newId = add(publisher, projection.text, moveToMonth, projection.type, dateFactory, idFactory);
		ItemMoved event = new ItemMoved(newId, moveToMonth, projection.id, dateFactory.now());
		applyAndPublish(publisher, event);
		return true;
	}

	public void applyAndPublish(EventBus publisher, ItemEvent event) {
		projection.apply(event);
		publisher.post(event);
	}

	private class DecisionProjection {

		private boolean exists = false;
		private ItemId id;
		private ItemType type;
		private boolean done = false;
		private boolean cancelled = false;
		private String text;

		public DecisionProjection(List<ItemEvent> history) {
			history.forEach(this::apply);
		}

		public void apply(ItemEvent event) {
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
