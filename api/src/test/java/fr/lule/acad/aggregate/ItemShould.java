package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

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
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.ItemEventSaver;

public class ItemShould {

	private IEventStore<IItemEvent, UUID> store;
	private EventBus bus;
	private UUID id;

	@Before
	public void before() {
		store = new InMemoryEventStore<IItemEvent, UUID>();
		bus = new EventBus();
		bus.register(new ItemEventSaver(store));
		id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
	}

	@Test
	public void raiseItemAddedWhenAddItem() {
		UUID id = Item.add(bus, "something", "2017-05", ItemType.EVENT);

		assertThat(store.getAllEvents()).contains(new ItemAdded(id, "something", "2017-05", ItemType.EVENT));
	}

	@Test
	public void raiseItemCancelledWhenCancelItem() {
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.cancel(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemCancelled(id));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemAlreadyCancelled() {
		store.add(new ItemCancelled(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemCancelled(id));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemDeleted() {
		store.add(new ItemDeleted(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemCancelled(id));
	}

	@Test
	public void raiseItemRestoredWhenRestoreItem() {
		store.add(new ItemCancelled(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.restore(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemRestored(id));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemNotCancelled() {
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemDeleted() {
		store.add(new ItemDeleted(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id));
	}

	@Test
	public void raiseItemDeletedWhenDeleteItem() {
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.delete(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemDeleted(id));
	}

	@Test
	public void dontRaiseItemDeletedWhenItemAlreadyDeleted() {
		store.add(new ItemDeleted(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.delete(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemDeleted(id));
	}

	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.completeTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		store.add(new TaskCompleted(id));
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenTaskDoesntExist() {
		UUID id = UUID.randomUUID();
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemNotTask() {
		id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.EVENT));
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getEventsFor(id)).doesNotContain(new TaskCompleted(id));
	}

	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		Item task = new Item(store.getEventsFor(id));

		boolean res1 = task.completeTask(bus);
		boolean res2 = task.completeTask(bus);

		assertThat(res1).isTrue();
		assertThat(res2).isFalse();
		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemDeleted() {
		store.add(new ItemDeleted(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id));
	}

	@Test
	public void raiseTaskUncompletedWhenUncompleteTask() {
		store.add(new TaskCompleted(id));
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskNotComplete() {
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskAlreadyUncompleted() {
		store.add(new TaskCompleted(id));
		store.add(new TaskUncompleted(id));
		Item task = new Item(store.getEventsFor(id));

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenNotATask() {
		UUID id = Item.add(bus, "something", "2017-05", ItemType.EVENT);
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenItemDeleted() {
		store.add(new TaskCompleted(id));
		store.add(new ItemDeleted(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id));
	}

	@Test
	public void raiseItemTextChangedWhenChangeItemText() {
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.changeItemText(bus, "new text");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemTextChanged(id, "new text"));
	}

	@Test
	public void raiseItemMovedAndItemAddedWhenMoveItem() {
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.moveItem(bus, "2017-06");

		assertThat(res).isTrue();
		ItemAdded created = (ItemAdded) store.getAllEvents().stream().filter(e -> e instanceof ItemAdded)
				.reduce((f, s) -> s).get();
		assertThat(created.getMonth()).isEqualTo("2017-06");
		assertThat(created.getText()).isEqualTo("buy baguette");
		assertThat(store.getAllEvents()).contains(new ItemMoved(id, created.getAggregateId(), "2017-06"));
	}

}
