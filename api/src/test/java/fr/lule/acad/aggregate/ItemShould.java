package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;
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
	public void raiseTaskCompletedWhenCompleteTask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item task = new Item(store.getEventsFor(id));
		
		task.completeTask(bus);
		
		assertThat(store.getEventsFor(id)).contains(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenCompleteTaskAlreadyCompleted() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		store.add(new TaskCompleted(id));
		Item task = new Item(store.getEventsFor(id));
		
		task.completeTask(bus);
		
		assertThat(store.getAllEvents()).containsOnlyOnce(new TaskCompleted(id));
	}

	@Test
	public void dontRaiseTaskCompletedWhenTaskDoesntExist() {
		UUID id = UUID.randomUUID();
		Item task = new Item(store.getEventsFor(id));
		
		task.completeTask(bus);
		
		assertThat(store.getAllEvents()).isEmpty();
	}
	
	@Test
	public void dontRaiseTaskCompletedWhenItemNotTask() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.EVENT));
		Item task = new Item(store.getEventsFor(id));
		
		task.completeTask(bus);
		
		assertThat(store.getEventsFor(id)).doesNotContain(new TaskCompleted(id));
	}
	
	@Test
	public void raiseTaskCompletedOnlyOnceWhenCompleteTaskTwice() {
		UUID id = UUID.randomUUID();
		store.add(new ItemAdded(id, "buy baguette", "2017-05", ItemType.TASK));
		Item task = new Item(store.getEventsFor(id));
		
		task.completeTask(bus);
		task.completeTask(bus);

		assertThat(store.getEventsFor(id)).containsOnlyOnce(new TaskCompleted(id));
	}
	
}
