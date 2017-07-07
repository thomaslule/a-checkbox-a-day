package fr.lule.acad.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;

public class InMemoryEventStoreShould {
	
	private IEventStore<IItemEvent> store;
	private UUID id1;
	private UUID id2;
	
	@Before
	public void before() {
		store = new InMemoryEventStore<IItemEvent>();
		id1 = UUID.randomUUID();
		ItemAdded item1 = new ItemAdded(id1, "task 1", "2017-05");
		id2 = UUID.randomUUID();
		ItemAdded item2 = new ItemAdded(id2, "task 2", "2017-05");
		TaskCompleted item3 = new TaskCompleted(id1);
		
		store.add(item1);
		store.add(item2);
		store.add(item3);
	}
	
	@Test
	public void getAllEventsRestituteEventsInSameOrder() {
		List<IItemEvent> events = store.getAllEvents();

		assertThat(events.get(0)).isEqualTo(new ItemAdded(id1, "task 1", "2017-05"));
		assertThat(events.get(1)).isEqualTo(new ItemAdded(id2, "task 2", "2017-05"));
		assertThat(events.get(2)).isEqualTo(new TaskCompleted(id1));
	}
	
	@Test
	public void getEventsForRestituteEventsForAggregate() {
		List<IItemEvent> events = store.getEventsFor(id1);

		assertThat(events.get(0)).isEqualTo(new ItemAdded(id1, "task 1", "2017-05"));
		assertThat(events.get(1)).isEqualTo(new TaskCompleted(id1));
	}
	
	

}
