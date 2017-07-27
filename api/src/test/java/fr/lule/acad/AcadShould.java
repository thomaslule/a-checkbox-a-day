package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.aggregate.Item;
import fr.lule.acad.aggregate.ItemType;
import fr.lule.acad.aggregate.Journal;
import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.projection.ItemDisplayed;
import fr.lule.acad.projection.ItemList;
import fr.lule.acad.projection.JournalEntry;
import fr.lule.acad.projection.JournalProjection;

public class AcadShould {

	@Test
	public void displayItemInItemListWhenAddItem() {
		EventBus bus = new EventBus();
		ItemList list = new ItemList(new ArrayList<IItemEvent>());
		bus.register(list);

		UUID id = Item.add(bus, "buy bread", "2017-05", ItemType.TASK);

		assertThat(list.getList("2017-05"))
				.contains(new ItemDisplayed(id, ItemType.TASK, "buy bread", "2017-05", false, false, false));
	}

	@Test
	public void displayJournalEntryWhenEditDay() {
		EventBus bus = new EventBus();
		JournalProjection journal = new JournalProjection(new ArrayList<IJournalEvent>());
		bus.register(journal);

		Journal.editJournalEntry(bus, "2017-07-19", "test");

		assertThat(journal.getJournal("2017-07")).contains(new JournalEntry("2017-07-19", "test"));
	}

}
