package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.aggregate.Item;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.aggregate.Journal;
import fr.lule.acad.event.ItemEvent;
import fr.lule.acad.event.ItemId;
import fr.lule.acad.event.JournalEvent;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalEntry;
import fr.lule.acad.projection.JournalProjection;

public class AcadShould {

	@Test
	public void displayItemInItemListWhenAddItem() {
		EventBus bus = new EventBus();
		ItemList list = new ItemList(new ArrayList<ItemEvent>());
		bus.register(list);

		ItemId id = Item.add(bus, "buy bread", "2017-05", ItemType.TASK, () -> new Date(0));

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id.getId(), ItemType.TASK, "buy bread", "2017-05", false, false, false));
	}

	@Test
	public void displayJournalEntryWhenEditDay() {
		EventBus bus = new EventBus();
		JournalProjection journal = new JournalProjection(new ArrayList<JournalEvent>());
		bus.register(journal);

		Journal.editJournalEntry(bus, "2017-07-19", "test", () -> new Date(0));

		assertThat(journal.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
	}

}
