package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.JournalDay;
import fr.lule.acad.event.JournalEntryEdited;
import fr.lule.acad.event.JournalEvent;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.JournalEventSaver;

public class JournalShould {

	@Test
	public void raiseJournalEntryEditedWhenEditJournalEntry() {
		IEventStore<JournalEvent, JournalDay> store = new InMemoryEventStore<JournalEvent, JournalDay>();
		EventBus bus = new EventBus();
		bus.register(new JournalEventSaver(store));

		boolean res = Journal.editJournalEntry(bus, "2017-07-19", "some text", () -> new Date(0));

		assertThat(res).isTrue();
		assertThat(store.getAllEvents())
				.contains(new JournalEntryEdited("some text", new JournalDay("2017-07-19"), new Date(0)));
	}

}
