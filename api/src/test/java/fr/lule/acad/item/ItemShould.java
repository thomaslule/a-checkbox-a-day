package fr.lule.acad.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.TestUtils;
import fr.lule.acad.event.IEventStore;
import fr.lule.acad.event.InMemoryEventStore;
import fr.lule.acad.item.Item;
import fr.lule.acad.item.ItemEventSaver;
import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.ItemType;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemCancelled;
import fr.lule.acad.item.event.ItemDeleted;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.ItemMoved;
import fr.lule.acad.item.event.ItemRestored;
import fr.lule.acad.item.event.ItemTextChanged;
import fr.lule.acad.item.event.TaskCompleted;
import fr.lule.acad.item.event.TaskUncompleted;
import fr.lule.acad.util.IdFactory;

public class ItemShould {

	private IEventStore<ItemEvent, ItemId> store;
	private EventBus bus;
	private ItemId id;

	@Before
	public void before() {
		store = new InMemoryEventStore<ItemEvent, ItemId>();
		bus = new EventBus();
		bus.register(new ItemEventSaver(store));
		id = new ItemId(UUID.randomUUID());
		store.add(new ItemAdded("buy baguette", "2017-05", ItemType.TASK, id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemAddedWhenAddItem() {
		ItemId id = Item.add(bus, "something", "2017-05", ItemType.EVENT, TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		assertThat(store.getAllEvents())
				.contains(new ItemAdded("something", "2017-05", ItemType.EVENT, id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemCancelledWhenCancelItem() {
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.cancel(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemCancelled(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemAlreadyCancelled() {
		store.add(new ItemCancelled(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemCancelled(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemDeleted() {
		store.add(new ItemDeleted(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemCancelled(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemRestoredWhenRestoreItem() {
		store.add(new ItemCancelled(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.restore(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemRestored(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemNotCancelled() {
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemDeleted() {
		store.add(new ItemDeleted(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemDeletedWhenDeleteItem() {
		store.add(new ItemCancelled(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.delete(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemDeleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseItemDeletedWhenItemAlreadyDeleted() {
		store.add(new ItemDeleted(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.delete(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemDeleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.completeTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		store.add(new TaskCompleted(id, TestUtils.DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskCompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenTaskDoesntExist() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		Item task = new Item(store.getEventsFor(id2), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id2, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemNotTask() {
		ItemId id2 = new ItemId(UUID.randomUUID());
		store.add(new ItemAdded("buy baguette", "2017-05", ItemType.EVENT, id2, TestUtils.DATE_ZERO));
		Item task = new Item(store.getEventsFor(id2), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getEventsFor(id2)).doesNotContain(new TaskCompleted(id2, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res1 = task.completeTask(bus);
		boolean res2 = task.completeTask(bus);

		assertThat(res1).isTrue();
		assertThat(res2).isFalse();
		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskCompletedWhenItemDeleted() {
		store.add(new ItemDeleted(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskCompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseTaskUncompletedWhenUncompleteTask() {
		store.add(new TaskCompleted(id, TestUtils.DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new TaskUncompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskNotComplete() {
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskAlreadyUncompleted() {
		store.add(new TaskCompleted(id, TestUtils.DATE_ZERO));
		store.add(new TaskUncompleted(id, TestUtils.DATE_ZERO));
		Item task = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskUncompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenNotATask() {
		ItemId id2 = Item.add(bus, "something", "2017-05", ItemType.EVENT, TestUtils.DATE_ZERO_FACTORY,
				new IdFactory());
		Item item = new Item(store.getEventsFor(id2), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id2, TestUtils.DATE_ZERO));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenItemDeleted() {
		store.add(new TaskCompleted(id, TestUtils.DATE_ZERO));
		store.add(new ItemDeleted(id, TestUtils.DATE_ZERO));
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemTextChangedWhenChangeItemText() {
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY, new IdFactory());

		boolean res = item.changeItemText(bus, "new text");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemTextChanged("new text", id, TestUtils.DATE_ZERO));
	}

	@Test
	public void raiseItemMovedAndItemAddedWhenMoveItem() {
		Item item = new Item(store.getEventsFor(id), TestUtils.DATE_ZERO_FACTORY,
				TestUtils.createIdFactory(new UUID(1, 0)));

		boolean res = item.moveItem(bus, "2017-06");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemAdded("buy baguette", "2017-06", ItemType.TASK,
				new ItemId(new UUID(1, 0)), TestUtils.DATE_ZERO));
		assertThat(store.getAllEvents())
				.contains(new ItemMoved(new ItemId(new UUID(1, 0)), "2017-06", id, TestUtils.DATE_ZERO));
	}

}
