package fr.lule.acad.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.ItemAdded;
import fr.lule.acad.event.TaskCompleted;

public class FileEventStoreShould {

	private FileEventStore<IItemEvent, UUID> store;

	@Test
	public void getAllEventsRestituteEventsFromFile() {
		store = new FileEventStore<IItemEvent, UUID>("src/test/resources/itemstore_saved.json");

		List<IItemEvent> events = store.getAllEvents();

		assertThat(events.get(0)).isInstanceOf(ItemAdded.class);
		assertThat(((ItemAdded) events.get(0)).getText()).isEqualTo("task 1");
		assertThat(events.get(1)).isInstanceOf(ItemAdded.class);
		assertThat(((ItemAdded) events.get(1)).getText()).isEqualTo("task 2");
		assertThat(events.get(2)).isInstanceOf(TaskCompleted.class);
	}

	@Test
	public void addStoreEventsToFile() throws IOException {
		store = new FileEventStore<IItemEvent, UUID>("src/test/resources/itemstore_temp.json");
		store.emptyStore();
		store.add(new ItemAdded(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0"), "task 1", "2017-05",
				ItemType.TASK));
		store.add(new ItemAdded(UUID.fromString("cfdb4cd7-7c33-47ac-9686-3400876ee30e"), "task 2", "2017-05",
				ItemType.TASK));
		store.add(new TaskCompleted(UUID.fromString("391dfb2e-2248-4ef6-b322-e9200496def0")));

		assertThat(readFile("./src/test/resources/itemstore_temp.json"))
				.isEqualTo(readFile("./src/test/resources/itemstore_saved.json"));
	}

	private static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

}
