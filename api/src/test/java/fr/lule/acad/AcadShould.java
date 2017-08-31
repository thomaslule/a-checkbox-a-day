package fr.lule.acad;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.item.Item;
import fr.lule.acad.item.ItemId;
import fr.lule.acad.item.ItemType;
import fr.lule.acad.item.event.ItemEvent;
import fr.lule.acad.item.projection.ItemDisplayed;
import fr.lule.acad.item.projection.ItemListProjection;
import fr.lule.acad.journal.Journal;
import fr.lule.acad.journal.event.JournalEvent;
import fr.lule.acad.journal.projection.JournalEntry;
import fr.lule.acad.journal.projection.JournalProjection;
import fr.lule.acad.util.IdFactory;

public class AcadShould {

	@Test
	public void displayItemInItemListWhenAddItem() {
		EventBus bus = new EventBus();
		ItemListProjection list = new ItemListProjection(new ArrayList<ItemEvent>());
		bus.register(list);

		ItemId id = Item.add(bus, "buy bread", "2017-05", ItemType.TASK, TestUtils.DATE_ZERO_FACTORY, new IdFactory());

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
