package fr.lule.acad.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;

import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.ItemType;
import fr.lule.acad.item.event.ItemAdded;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.event.TaskCompleted;

public class FileEventStoreShould {

	private final static Date DATE_ZERO = new Date(0);
	private final static String FILENAME = "src/test/resources/temp.json";

	private List<Class<? extends ItemEvent>> eventClasses;

	@Before
	public void before() {
		eventClasses = Arrays.asList(ItemAdded.class, TaskCompleted.class);
	}

	@After
	public void after() {
		new File(FILENAME).delete();
	}

	@Test
	public void storeAndRestituteEventsFromFile() {
		FileEventStore<ItemEvent, ItemId> store = new FileEventStore<ItemEvent, ItemId>(FILENAME, eventClasses);

		ItemAdded event1 = new ItemAdded("task 1", "2017-05", ItemType.TASK,
				new ItemId(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0")), DATE_ZERO);
		ItemAdded event2 = new ItemAdded("task 2", "2017-05", ItemType.TASK,
				new ItemId(UUID.fromString("cfdb4cd7-7c33-47ac-9686-3400876ee30e")), DATE_ZERO);
		TaskCompleted event3 = new TaskCompleted(new ItemId(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0")),
				DATE_ZERO);

		store.add(event1);
		store.add(event2);
		store.add(event3);

		store = new FileEventStore<ItemEvent, ItemId>(FILENAME, eventClasses);

		List<ItemEvent> events = store.getAllEvents();

		assertThat(events.size()).isEqualTo(3);
		assertThat(events.get(0)).isEqualTo(event1);
		assertThat(events.get(1)).isEqualTo(event2);
		assertThat(events.get(2)).isEqualTo(event3);
	}

	@Test
	public void doesntCrashWhenLoadingEmptyFile() throws IOException {
		Files.touch(new File(FILENAME));
		FileEventStore<ItemEvent, ItemId> store = new FileEventStore<ItemEvent, ItemId>(FILENAME, eventClasses);

		assertThat(store.getAllEvents().size()).isEqualTo(0);
	}
}
