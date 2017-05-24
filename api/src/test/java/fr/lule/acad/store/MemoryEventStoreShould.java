package fr.lule.acad.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEvent;
import fr.lule.acad.event.TaskAdded;
import fr.lule.acad.event.TaskCompleted;

public class MemoryEventStoreShould {
	
	private IEventStore store;
	private UUID id1;
	private UUID id2;
	
	@Before
	public void before() {
		store = new MemoryEventStore();
		id1 = UUID.randomUUID();
		TaskAdded task1 = new TaskAdded(id1, "task 1");
		id2 = UUID.randomUUID();
		TaskAdded task2 = new TaskAdded(id2, "task 2");
		TaskCompleted task3 = new TaskCompleted(id1);
		
		store.add(task1);
		store.add(task2);
		store.add(task3);
	}
	
	@Test
	public void getAllEventsRestituteEventsInSameOrder() {
		List<IEvent> events = store.getAllEvents();

		assertThat(events.get(0)).isEqualTo(new TaskAdded(id1, "task 1"));
		assertThat(events.get(1)).isEqualTo(new TaskAdded(id2, "task 2"));
		assertThat(events.get(2)).isEqualTo(new TaskCompleted(id1));
	}
	
	@Test
	public void getEventsForRestituteEventsForAggregate() {
		List<IEvent> events = store.getEventsFor(id1);

		assertThat(events.get(0)).isEqualTo(new TaskAdded(id1, "task 1"));
		assertThat(events.get(1)).isEqualTo(new TaskCompleted(id1));
	}
	
	

}
