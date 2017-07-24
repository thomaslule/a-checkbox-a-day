package fr.lule.acad.aggregate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import fr.lule.acad.event.IItemEvent;
import fr.lule.acad.event.IJournalEvent;
import fr.lule.acad.event.JournalEntryEdited;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;

public class JournalShould {

	@Test
	public void raiseJournalEntryEditedWhenEditJournalEntry() {
		InMemoryEventStore<IJournalEvent> store = new InMemoryEventStore<IJournalEvent>();
		EventsBus bus = new EventsBus(new InMemoryEventStore<IItemEvent>(), store);

		boolean res = Journal.editJournalEntry(bus, "2017-07-19", "some text");

		assertThat(res).isTrue();
		assertThat(store.getAllEvents()).contains(new JournalEntryEdited("2017-07-19", "some text"));
	}

}
