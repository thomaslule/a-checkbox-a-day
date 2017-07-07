package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemCancelled;
import fr.lule.acad.event.ItemRestored;
import fr.lule.acad.event.TaskCompleted;
import fr.lule.acad.event.TaskUncompleted;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;

public class ItemShould {
	
	InMemoryEventStore<IItemEvent> store;
	EventsBus bus;
	
	@Before
	public void before() {
		store = new InMemoryEventStore<IItemEvent>();
		bus = new EventsBus(store);
	}

	@Test
	public void raiseItemAddedWhenAddItem() {
		UUID id = Item.add(bus, "buy baguette", "2017-05", ItemType.TASK);
		
		assertThat(store.getAllEvents()).contains(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
	}

	@Test
	public void raiseItemCancelledWhenCancelItem() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.cancel(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemCancelled(id));
	}

	@Test
	public void dontRaiseItemCancelledWhenItemAlreadyCancelled() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		store.add(new ItemCancelled(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.cancel(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new ItemCancelled(id));
	}

	@Test
	public void raiseItemRestoredWhenRestoreItem() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		store.add(new ItemCancelled(id));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.restore(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new ItemRestored(id));
	}

	@Test
	public void dontRaiseItemRestoredWhenItemNotCancelled() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item item = new Item(store.getEventsFor(id));

		boolean res = item.restore(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new ItemRestored(id));
	}
	
	@Test
	public void raiseTaskCompletedWhenCompleteTask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.completeTask(bus);
		
		assertThat(res).isTrue();
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
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
		assertThat(store.getAllEvents()).isEmpty();
	}
	
	@Test
	public void dontRaiseTaskCompletedWhenItemNotTask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.EVENT));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.completeTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getEventsFor(id)).doesNotContain(new TaskCompleted(id));
	}
	
	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res1 = task.completeTask(bus);
		boolean res2 = task.completeTask(bus);

		assertThat(res1).isTrue();
		assertThat(res2).isFalse();
		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id));
	}

	@Test
	public void raiseTaskUncompletedWhenUncompleteTask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		store.add(new TaskCompleted(id));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.uncompleteTask(bus);

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskNotComplete() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id));
	}

	@Test
	public void dontRaiseTaskUncompletedWhenTaskAlreadyUncompleted() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		store.add(new TaskCompleted(id));
		store.add(new TaskUncompleted(id));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskUncompleted(id));
	}
	
	@Test
	public void dontRaiseTaskUncompletedWhenNotATask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.EVENT));
		Item task = new Item(store.getEventsFor(id));
		
		boolean res = task.uncompleteTask(bus);

		assertThat(res).isFalse();
		assertThat(store.getAllEvents()).doesNotContain(new TaskUncompleted(id));
	}
}
