package fr.lule.acad.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.TaskCompleted;

public class FileEventStoreShould {

	private final Date DATE_ZERO = new Date(0);
	private final String FILENAME = "src/test/resources/temp.json";

	@After
	public void after() {
		new File(FILENAME).delete();
	}

	@Test
	public void storeAndRestituteEventsFromFile() {
		FileEventStore<ItemEvent, ItemId> store = new FileEventStore<ItemEvent, ItemId>(FILENAME);

		ItemAdded event1 = new ItemAdded("task 1", "2017-05", ItemType.TASK,
				new ItemId(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0")), DATE_ZERO);
		ItemAdded event2 = new ItemAdded("task 2", "2017-05", ItemType.TASK,
				new ItemId(UUID.fromString("cfdb4cd7-7c33-47ac-9686-3400876ee30e")), DATE_ZERO);
		TaskCompleted event3 = new TaskCompleted(new ItemId(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0")),
				DATE_ZERO);

		store.add(event1);
		store.add(event2);
		store.add(event3);

		store = new FileEventStore<ItemEvent, ItemId>("src/test/resources/temp.json");

		List<ItemEvent> events = store.getAllEvents();

		assertThat(events.size()).isEqualTo(3);
		assertThat(events.get(0)).isEqualTo(event1);
		assertThat(events.get(1)).isEqualTo(event2);
		assertThat(events.get(2)).isEqualTo(event3);
	}
}
