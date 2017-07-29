package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemDeleted;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.ItemMoved;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.ItemTextChanged;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.ItemEventSaver;
import fr.lule.acad.util.IDateFactory;

public class ItemShould {

	private final Date DATE_ZERO = new Date(0);

	private IEventStore<ItemEvent, ItemId> store;
	private EventBus bus;
	private ItemId id;
	private IDateFactory fakeDateFactory = () -> DATE_ZERO;

	@Before
	public void before() {
		store = new InMemoryEventStore<ItemEvent, ItemId>();
		bus = new EventBus();
		bus.register(new ItemEventSaver(store));
		id = new ItemId(UUID.randomUUID());
		store.add(new ItemAdded("buy baguette", "2017-05", ItemType.TASK, id, DATE_ZERO));
	}

	@Test
	public void raiseItemAddedWhenAddItem() {
		ItemId id = Item.add(bus, "something", "2017-05", ItemType.EVENT, fakeDateFactory);

		assertThat(store.getAllEvents()).contains(new ItemAdded("something", "2017-05", ItemType.EVENT, id, DATE_ZERO));
	}

	@Test
	public void raiseItemCancelledWhenCancelItem() {
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.cancel(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemCancelled(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemAlreadyCancelled() {
		store.add(new ItemCancelled(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemCancelled(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemDeleted() {
		store.add(new ItemDeleted(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemCancelled(id, DATE_ZERO));
	}

	@Test
	public void raiseItemRestoredWhenRestoreItem() {
		store.add(new ItemCancelled(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.restore(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemRestored(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemNotCancelled() {
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemDeleted() {
		store.add(new ItemDeleted(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id, DATE_ZERO));
	}

	@Test
	public void raiseItemDeletedWhenDeleteItem() {
		store.add(new ItemCancelled(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.delete(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemDeleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseItemDeletedWhenItemAlreadyDeleted() {
		store.add(new ItemDeleted(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.delete(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemDeleted(id, DATE_ZERO));
	}

	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = task.completeTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		store.add(new TaskCompleted(id, DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskCompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenTaskDoesntExist() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		Item task = new Item(store.getEventsFor(id2), fakeDateFactory);

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id2, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemNotTask() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		store.add(new ItemAdded("buy baguette", "2017-05", ItemType.EVENT, id2, DATE_ZERO));
		Item task = new Item(store.getEventsFor(id2), fakeDateFactory);

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getEventsFor(id2)).doesNotContain(new TaskCompleted(id2, DATE_ZERO));
	}

	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res1 = task.completeTask(bus);
		boolean res2 = task.completeTask(bus);

		assertThat(res1).isTrue();
		assertThat(res2).isFalse();
		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemDeleted() {
		store.add(new ItemDeleted(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id, DATE_ZERO));
	}

	@Test
	public void raiseTaskUncompletedWhenUncompleteTask() {
		store.add(new TaskCompleted(id, DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new TaskUncompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskNotComplete() {
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskAlreadyUncompleted() {
		store.add(new TaskCompleted(id, DATE_ZERO));
		store.add(new TaskUncompleted(id, DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskUncompleted(id, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenNotATask() {
		ItemId id2 = Item.add(bus, "something", "2017-05", ItemType.EVENT, fakeDateFactory);
		Item item = new Item(store.getEventsFor(id2), fakeDateFactory);

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id2, DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenItemDeleted() {
		store.add(new TaskCompleted(id, DATE_ZERO));
		store.add(new ItemDeleted(id, DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id, DATE_ZERO));
	}

	@Test
	public void raiseItemTextChangedWhenChangeItemText() {
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.changeItemText(bus, "new text");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemTextChanged("new text", id, DATE_ZERO));
	}

	@Test
	public void raiseItemMovedAndItemAddedWhenMoveItem() {
		Item item = new Item(store.getEventsFor(id), fakeDateFactory);

		boolean res = item.moveItem(bus, "2017-06");

		assertThat(res).isTrue();
		ItemAdded created = (ItemAdded) store.getAllEvents().stream().filter(e -> e instanceof ItemAdded)
				.reduce((f, s) -> s).get();
		assertThat(created.getMonth()).isEqualTo("2017-06");
		assertThat(created.getText()).isEqualTo("buy baguette");
		assertThat(store.getAllEvents()).contains(new ItemMoved(created.getAggregateId(), "2017-06", id, DATE_ZERO));
	}

}
