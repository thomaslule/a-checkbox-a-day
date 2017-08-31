package fr.lule.acad.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.lule.acad.event.IEventStore;
import fr.lule.acad.event.InMemoryEventStore;
import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.ItemType;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.TaskCompleted;

public class InMemoryEventStoreShould {

	private final Date DATE_ZERO = new Date(0);

	private IEventStore<ItemEvent, ItemId> store;
	private ItemId id1;
	private ItemId id2;

	@Before
	public void before() {
		store = new InMemoryEventStore<ItemEvent, ItemId>();
		id1 = new ItemId(UUID.randomUUID());
		ItemAdded item1 = new ItemAdded("task 1", "2017-05", ItemType.TASK, id1, DATE_ZERO);
		id2 = new ItemId(UUID.randomUUID());
		ItemAdded item2 = new ItemAdded("task 2", "2017-05", ItemType.TASK, id2, DATE_ZERO);
		TaskCompleted item3 = new TaskCompleted(id1, DATE_ZERO);

		store.add(item1);
		store.add(item2);
		store.add(item3);
	}

	@Test
	public void getAllEventsRestituteEventsInSameOrder() {
		List<ItemEvent> events = store.getAllEvents();

		assertThat(events.get(0)).isEqualTo(new ItemAdded("task 1", "2017-05", ItemType.TASK, id1, DATE_ZERO));
		assertThat(events.get(1)).isEqualTo(new ItemAdded("task 2", "2017-05", ItemType.TASK, id2, DATE_ZERO));
		assertThat(events.get(2)).isEqualTo(new TaskCompleted(id1, DATE_ZERO));
	}

	@Test
	public void getEventsForRestituteEventsForAggregate() {
		List<ItemEvent> events = store.getEventsFor(id1);

		assertThat(events.get(0)).isEqualTo(new ItemAdded("task 1", "2017-05", ItemType.TASK, id1, DATE_ZERO));
		assertThat(events.get(1)).isEqualTo(new TaskCompleted(id1, DATE_ZERO));
	}

}
