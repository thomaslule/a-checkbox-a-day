package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.event.JournalEntryEdited;
import fr.lule.acad.store.IEventStore;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.store.JournalEventSaver;

public class JournalShould {

	@Test
	public void raiseJournalEntryEditedWhenEditJournalEntry() {
		IEventStore<IJournalEvent, String> store = new InMemoryEventStore<IJournalEvent, String>();
		EventBus bus = new EventBus();
		bus.register(new JournalEventSaver(store));

		boolean res = Journal.editJournalEntry(bus, "2017-07-19", "some text");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new JournalEntryEdited("2017-07-19", "some text"));
	}

}
